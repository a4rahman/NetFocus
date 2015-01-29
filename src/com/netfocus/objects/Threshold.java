package com.netfocus.objects;

/**
 * A threshold defines a set of maximum values for each severity level
 * for a metric, beyond which an alarm is raised for that pair.
 * 
 * For example, a metric may have the following maximums:
 * 
 * WARNING: 75%
 * CRITICAL: 85%
 * FATAL: 95%
 * 
 * These values would be recorded in a threshold, so that any service trying to
 * create alarms against the metric can look them up and decide whether to raise
 * an alarm and if so, of what severity.
 * 
 * @author Asif Rahman
 *
 */
public class Threshold {
	private double warningThreshold;
	private double criticalThreshold;
	private double fatalThreshold;
	
	/**
	 * Construct a Threshold instance with max values for each severity level
	 * 
	 * @param warningThreshold the warning level max
	 * @param criticalThreshold the critical level max
	 * @param fatalThreshold the fatal level max
	 */
	public Threshold(double warningThreshold, double criticalThreshold, double fatalThreshold) {
		this.warningThreshold = warningThreshold;
		this.criticalThreshold = criticalThreshold;
		this.fatalThreshold = fatalThreshold;
	}

	public double getWarningThreshold() {
		return warningThreshold;
	}

	public void setWarningThreshold(double warningThreshold) {
		this.warningThreshold = warningThreshold;
	}

	public double getCriticalThreshold() {
		return criticalThreshold;
	}

	public void setCriticalThreshold(double criticalThreshold) {
		this.criticalThreshold = criticalThreshold;
	}

	public double getFatalThreshold() {
		return fatalThreshold;
	}

	public void setFatalThreshold(double fatalThreshold) {
		this.fatalThreshold = fatalThreshold;
	}

}
