package com.netfocus.alarms.scoring;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.netfocus.alarms.management.AlarmManager;
import com.netfocus.alarms.scoring.AlarmScorer;
import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

/**
 * Unit tests for @AlarmScorer.
 *
 * @author Asif Rahman
 *
 */
public class AlarmScorerTest {
    private final AlarmManager alarmManager = AlarmManager.getInstance();
    private final AlarmScorer alarmScorer = new AlarmScorer();

    private static final String MEMORY_UTILIZATION = "memoryUtilization";
    private static final String CPU_UTILIZATION = "cpuUtilization";

    @After
    public void cleanup() {
        alarmManager.clearAllAlarmsAndThresholds();
    }

    @Test
    public void testGetMostCriticalAlarmWithAlarmsOfDifferentSeverity() {
        final Node node = setupAlarmsAndGetSampleNode(87, 97);
        final Alarm alarm = alarmScorer.getMostCriticalAlarm(node);
        Assert.assertEquals(MEMORY_UTILIZATION, alarm.getMetric().getName());
    }

    @Test
    public void testGetMostCriticalAlarmWithAlarmsOfSameSeverityAndMetricValue() {
        final Node node = setupAlarmsAndGetSampleNode(87, 87);
        // The severities are the same and so are the metric values, so the % by which the
        // metrics exceed the threhsold should be the same. This means any of the two metrics
        // could be deemed the most critical.
        Assert.assertNotNull(alarmScorer.getMostCriticalAlarm(node));
    }

    @Test
    public void testGetMostCriticalAlarmWithAlarmsOfSameSeverityAndDifferentMetricValues() {
        final Node node = setupAlarmsAndGetSampleNode(88, 87);
        final Alarm alarm = alarmScorer.getMostCriticalAlarm(node);
        // Both alarms are of same severity, but CPU utilization has exceeded
        // the threshold by a greater %
        Assert.assertEquals(CPU_UTILIZATION, alarm.getMetric().getName());
    }

    @Test
    public void testGetMostCriticalAlarmWhenThereAreNoAlarms() {
        final Node node = setupAlarmsAndGetSampleNode(74, 77);
        // Both metrics have normal values, so no alarms!
        Assert.assertNull(alarmScorer.getMostCriticalAlarm(node));
    }

    private Node setupAlarmsAndGetSampleNode(double cpuUtilization, double memoryUtilization) {
        // Set up node with metrics. Just two metrics should be sufficient to test most cases.
        final Node node = new Node("Host-85");
        final Metric metric1 = new Metric(CPU_UTILIZATION, node.getName());
        metric1.setValue(cpuUtilization);
        final Metric metric2 = new Metric(MEMORY_UTILIZATION, node.getName());
        metric2.setValue(memoryUtilization);
        node.getMetrics().add(metric1);
        node.getMetrics().add(metric2);

        final Threshold utilizationThreshold = new Threshold(85, 90, 95);

        // Register the thresholds
        alarmManager.registerThreshold(metric1, utilizationThreshold);
        alarmManager.registerThreshold(metric2, utilizationThreshold);

        // Trigger alarms
        alarmManager.triggerAlarms(node);

        return node;
    }

}
