package com.netfocus.main;

import java.util.ArrayList;
import java.util.List;

import com.netfocus.alarms.management.AlarmManager;
import com.netfocus.alarms.scoring.AlarmScorer;
import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

public class NetFocusMain {
	private static List<Node> NODES = new ArrayList<Node>();
	private static AlarmManager ALARM_MANAGER = AlarmManager.getInstance();
	
	private static void setup() {
		final Node node1 = new Node("Host-85");
		final Metric metric1 = new Metric("latency", node1.getName());
		metric1.setValue(21);
		final Metric metric2 = new Metric("memoryUtilization", node1.getName());
		metric2.setValue(97);
		node1.getMetrics().add(metric1);
		node1.getMetrics().add(metric2);
		
		final Node node2 = new Node("VM-20");
		final Metric metric3 = new Metric("cpuUtilization", node2.getName());
		metric3.setValue(87);
		final Metric metric4 = new Metric("memoryUtilization", node2.getName());
		metric4.setValue(87);
		node2.getMetrics().add(metric3);
		node2.getMetrics().add(metric4);
		
		NODES.add(node1);
		NODES.add(node2);
		
		final Threshold latencyThreshold = new Threshold(10, 15, 20);
		final Threshold utilizationThreshold = new Threshold(85, 90, 95);
		
		ALARM_MANAGER.registerThreshold(metric1, latencyThreshold);
		ALARM_MANAGER.registerThreshold(metric2, utilizationThreshold);
		ALARM_MANAGER.registerThreshold(metric3, utilizationThreshold);
		ALARM_MANAGER.registerThreshold(metric4, utilizationThreshold);
		
		ALARM_MANAGER.triggerAlarms(node1);
		ALARM_MANAGER.triggerAlarms(node2);
	}
	
	public static void main(String[] args) {
		setup();
		final AlarmScorer alarmScorer = new AlarmScorer();
		for (Node n : NODES) {
			final Alarm mostCriticalAlarm = alarmScorer.getMostCriticalAlarm(n);
			if (mostCriticalAlarm == null) {
				System.out.println("No alarms for node " + n.getName());
				continue;
			}
			final Metric mostCriticalMetric = mostCriticalAlarm.getMetric();
			System.out.println("Most critical issue for node " + n.getName() 
					+ ": metric " + mostCriticalMetric.getName() 
					+ " with severity " + mostCriticalAlarm.getSeverity() 
					+ " and value of " + mostCriticalMetric.getValue());
		}
	}
}
