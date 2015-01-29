package com.netfocus.objects;

import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a network node. This could be a host, a VM, a NIC, etc.
 *
 * @author Asif Rahman
 *
 */
public class Node {
    private final String name;
    
    /** Metric name -> Metric instance map **/
    private final Set<Metric> metrics;

    /**
     * Construct a network node.
     *
     * @param name the unique name of the node
     */
    public Node(String name) {
    	if (name == null || name.isEmpty()) {
    		throw new IllegalArgumentException("Must provide a name");
    	}
        this.name = name;
        this.metrics = new HashSet<Metric>();
    }

    public Set<Metric> getMetrics() {
        return metrics;
    }

    public String getName() {
        return name;
    }

}
