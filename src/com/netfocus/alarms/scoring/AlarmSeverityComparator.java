package com.netfocus.alarms.scoring;

import java.util.Comparator;

import com.netfocus.objects.Alarm;

/**
 * Compare alarms by severity.
 *
 * @author Asif Rahman
 *
 */
public class AlarmSeverityComparator implements Comparator<Alarm> {

    @Override
    public int compare(Alarm alarm1, Alarm alarm2) {
        // Descending order is what we seek
        return alarm2.getSeverity().getValue() - alarm1.getSeverity().getValue();
    }

}
