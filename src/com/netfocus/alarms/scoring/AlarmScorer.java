package com.netfocus.alarms.scoring;

import java.util.Collections;
import java.util.List;

import com.netfocus.alarms.management.AlarmManager;
import com.netfocus.constants.Severity;
import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

/**
 * This class performs calculations to determine the most critical issue for a network node.
 * It does so by considering the severities of all of the active alarms raised against the
 * node in the system.
 *
 * @author Asif Rahman
 *
 */
public class AlarmScorer {

	private final AlarmManager alarmManager = getAlarmManager();
	
    public Alarm getMostCriticalAlarm(Node node) {

        // Get all alarms for the node
        List<Alarm> alarms = alarmManager.getAlarms(node);

        if (alarms == null || alarms.isEmpty()) {
            // No alarms, yay!
            return null;
        }

        // Sort by descending order of severity
        sortAlarmsBySeverity(alarms);

        Alarm firstAlarm = alarms.get(0);
        Severity highestSeverity = firstAlarm.getSeverity();
        if (alarms.size() < 2 || alarms.get(1).getSeverity().getValue() < highestSeverity.getValue()) {
            // There is only one alarm with the highest priority. Easy choice, just return the alarm and we're done.
            return firstAlarm;
        } 
        // Things get interesting here. Now we have more than one alarm with the highest severity.
        return getMostCriticalAlarm(alarms, highestSeverity);
    }

    private Alarm getMostCriticalAlarm(List<Alarm> alarms, Severity highestSeverity) {
        double maxPercentExceeded = 0;
        Alarm mostCriticalAlarm = null;
        for (Alarm alarm : alarms) {
            if (alarm.getSeverity() != highestSeverity) {
                // We've moved on to lower severities and are done.
                break;
            }
            final Metric metric = alarm.getMetric();
            final double metricValue = metric.getValue();
            final Threshold threshold = alarmManager.getThreshold(metric);
            double thresholdValue = getThresholdValueBySeverity(threshold, highestSeverity);
            if (thresholdValue > 0) {
                final double percentExceeded = getPercentExceeded(metricValue, thresholdValue);
                // If percentExceeded == maxPercentExceeded, we don't need to update the max;
                // in other words, if we have two metrics that have exceeded their respective
                // thresholds by the same %, we can pick any one of the two as the max.
                if (percentExceeded > maxPercentExceeded) {
                	maxPercentExceeded = percentExceeded;
                	mostCriticalAlarm = alarm;
                }
            }
        }
        return mostCriticalAlarm;
    }

    private double getThresholdValueBySeverity(Threshold threshold, Severity severity) {
        if (Severity.WARNING == severity) {
            return threshold.getWarningThreshold();
        } else if (Severity.CRITICAL == severity) {
            return threshold.getCriticalThreshold();
        } else if (Severity.FATAL == severity) {
            return threshold.getFatalThreshold();
        }
        // The NORMAL severity case
        return -1;
    }

    private double getPercentExceeded(double metricValue, double thresholdValue) {
        // Not really a percentage, more like a factor. Sufficient for comparison purposes.
        return (metricValue - thresholdValue)/thresholdValue;
    }

    private AlarmManager getAlarmManager() {
        return AlarmManager.getInstance();
    }

    void sortAlarmsBySeverity(List<Alarm> alarms) {
        Collections.sort(alarms, new AlarmSeverityComparator());
    }

}
