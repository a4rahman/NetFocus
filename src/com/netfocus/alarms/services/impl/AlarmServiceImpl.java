package com.netfocus.alarms.services.impl;

import com.netfocus.alarms.services.AlarmService;
import com.netfocus.constants.Severity;
import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

/**
 * Implementation of @AlarmService.
 *
 * @author Asif Rahman
 *
 */
public class AlarmServiceImpl implements AlarmService {

    @Override
    public Alarm triggerAlarm(Node node, Metric metric, Threshold threshold) {
    	if (node == null || metric == null || threshold == null) {
    		throw new IllegalArgumentException("Parameters node, metric and threshold must not be null");
    	}

    	if (!node.getMetrics().contains(metric)) {
    		throw new IllegalArgumentException("Node " + node.getName() + " doesn't contain metric " + metric.getName());
    	}
    	
        final double metricValue = metric.getValue();
        final Severity severity = getSeverity(metricValue, threshold);
        if (severity != Severity.NORMAL) {
        	// Need to raise an alarm
        	return new Alarm(node, metric, severity);
        }
        return null;
    }

    private Severity getSeverity(double metricValue, Threshold threshold) {
    	// Compare a metric value with the max for each severity in the
    	// given threshold to determine which severity level it exceeds
    	if (metricValue >= threshold.getFatalThreshold()) {
    		return Severity.FATAL;
    	} else if (metricValue >= threshold.getCriticalThreshold()) {
    		return Severity.CRITICAL;
    	} else if (metricValue >= threshold.getWarningThreshold()) {
    		return Severity.WARNING;
    	}
    	return Severity.NORMAL;
    }

}
