package com.netfocus.objects;

import com.netfocus.constants.Severity;

/**
 * Representation of an alarm, raised when a metric value exceeds a predefined threshold.
 *
 * @author Asif Rahman
 *
 */
public class Alarm {
    private final String id;
    private final Node node;
    private final Metric metric;
    private final Severity severity;

    /**
     * Construct and Alarm instance.
     *
     * @param node the network node that the alarm was triggered against
     * @param metric the metric that the alarm was triggered against
     * @param severity the severity of the alarm
     */
    public Alarm(Node node, Metric metric, Severity severity) {
        this.id = node.getName() + metric.getName();
        this.node = node;
        this.metric = metric;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public Node getNode() {
        return node;
    }

    public Metric getMetric() {
        return metric;
    }

    public Severity getSeverity() {
        return severity;
    }

}
