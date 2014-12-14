package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 *
 * @author Artem Zaborskiy
 *
 */
public class DateTimeEditDto {
    private int hours;

    private int minutes;

    private String startDateStr;

    public DateTimeEditDto() {
    }

    public DateTimeEditDto(final DateTime dt) {
        this.hours = dt.getHourOfDay();
        this.minutes = dt.getMinuteOfHour();
        this.startDateStr = ISODateTimeFormat.dateTime().print(dt);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setHours(final int hours) {
        this.hours = hours;
    }

    public void setMinutes(final int minutes) {
        this.minutes = minutes;
    }

    public void setStartDateStr(final String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public DateTime toDateTime() {
        if (this.getStartDateStr() != null) {
            DateTime t = DateTime.parse(this.getStartDateStr());
            t = t.withHourOfDay(this.getHours());
            t = t.withMinuteOfHour(this.getMinutes());
            return t;
        } else {
            return null;
        }
    }
}
