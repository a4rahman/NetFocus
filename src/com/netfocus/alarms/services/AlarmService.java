package com.netfocus.alarms.services;

import com.netfocus.objects.Alarm;
import com.netfocus.objects.Metric;
import com.netfocus.objects.Node;
import com.netfocus.objects.Threshold;

/**
 * Service to help create/handle alarms.
 *
 * @author Asif Rahman
 *
 */
public interface AlarmService {
    /**
     * Trigger an alarm for a particular metric of a node (if applicable, of course).
     * @param node the network node to trigger an alarm against
     * @param metric the metric to trigger an alarm for
     * @param threshold the threshold to use
     * @return the alarm
     */
    Alarm triggerAlarm(Node node, Metric metric, Threshold threshold);
}
