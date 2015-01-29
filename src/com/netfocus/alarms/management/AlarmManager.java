package com.netfocus.alarms.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.netfocus.alarms.services.impl.AlarmServiceImpl;
import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

/**
 * Create and store alarms.
 *
 * @author Asif Rahman
 *
 */
public class AlarmManager {
	private static AlarmManager instance;

	/** Map to store all thresholds for all metrics */
	private final Map<Metric, Threshold> metricToThresholdMap = new HashMap<>();

	/** Map to store all alarms for all nodes */
	private final Map<String, List<Alarm>> nodeToAlarmMap = new HashMap<>();

	private final AlarmServiceImpl alarmService;

	private AlarmManager() {
		alarmService = new AlarmServiceImpl();
	}

	/**
	 * Get the only AlarmManager instance.
	 *
	 * @return the AlarmManager instance
	 */
	public static AlarmManager getInstance() {
		if (instance == null) {
			instance = new AlarmManager();
		}
		return instance;
	}

	/**
	 * Trigger alarms on a network node.
	 *
	 * @param node the network node to trigger on
	 */
	public void triggerAlarms(Node node) {
		final Set<Metric> metrics = node.getMetrics();
		for (Metric metric : metrics) {
			if (metricToThresholdMap.containsKey(metric)) {
				final String nodeName = node.getName();
				// Generate alarm
				try {
					final Alarm alarm = alarmService.triggerAlarm(node, metric, metricToThresholdMap.get(metric));
					if (alarm != null) {
						if (!nodeToAlarmMap.containsKey(nodeName)) {
							nodeToAlarmMap.put(nodeName, new ArrayList<Alarm>());
						}
						// Store the alarm
						nodeToAlarmMap.get(nodeName).add(alarm);
					}
				} catch (IllegalArgumentException iae) {
					// Log and continue
					System.err.println("Encountered exception while trying to trigger an alarm for "
							+ nodeName + ", moving on to the next metric. The exception: " + iae.getMessage());
				}
			}
		}
	}

	/**
	 * Get all triggered alarms for a network node.
	 *
	 * @param node the network node
	 * @return all alarms for the node
	 */
	public List<Alarm> getAlarms(Node node) {
		return node != null ? nodeToAlarmMap.get(node.getName()) : null;
	}

	/**
	 * Store a threshold for a metric.
	 *
	 * @param metric the metric
	 * @param threshold the threshold to store
	 */
	public void registerThreshold(Metric metric, Threshold threshold) {
		if (metric != null && threshold != null) {
			metricToThresholdMap.put(metric, threshold);
			return;
		}
		throw new IllegalArgumentException("Must provide a metric name and a threshold");
	}

	public Threshold getThreshold(Metric metric) {
	    return metric != null ? metricToThresholdMap.get(metric) : null;
	}

	public void clearAllAlarmsAndThresholds() {
	    clearAllAlarms();
	    clearAllThresholds();
	}

	public void clearAllAlarms() {
	    nodeToAlarmMap.clear();
	}

	public void clearAllThresholds() {
	    metricToThresholdMap.clear();
	}
}
