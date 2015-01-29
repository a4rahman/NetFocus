package com.netfocus.objects;

/**
 * Representation of a metric. A metric is a measurement of the performance of a network node.
 * For instance, the metric "memoryConsumed" of a virtual machine would be a measure of
 * how much memory the virtual machine is consuming.
 *
 * @author Asif Rahman
 *
 */
public class Metric {
    private final String name;
    private final String nodeName;
    // TODO: Extend this to support other data types
    private double value;

    /**
     * Construct a metric.
     * @param name the name of the metric
     * @param nodeName the name of the network node this metric is under
     */
    public Metric(String name, String nodeName) {
    	if (name == null || name.isEmpty() || nodeName == null) {
    		throw new IllegalArgumentException("Must provide a metric name and a node name");
    	}
        this.name = name;
        this.nodeName = nodeName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

	public String getNodeName() {
		return nodeName;
	}
    
}
