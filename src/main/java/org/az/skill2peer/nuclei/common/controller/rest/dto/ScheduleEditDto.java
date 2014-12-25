package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;
import com.google.ical.values.Weekday;
import com.google.ical.values.WeekdayNum;

/**
 *
 *
 * @author Artem Zaborskiy
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleEditDto {
    private static final String DATE_FORMAT_MONTH = "MMMM";

    /**
     * duration in minutes;
     */
    private int duration;

    private Boolean[] repeatDays = new Boolean[7];

    private DateTimeEditDto dateTime;

    private String next;

    private boolean recurrent;

    public DateTimeEditDto getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        final Period period = Period.minutes(duration);
        return PeriodFormat.wordBased(LocaleContextHolder.getLocale())
                .print(period.normalizedStandard(period.getPeriodType()));
    }

    public DateTime getEnd() {
        final DateTime s = new DateTime(dateTime.toDateTime());
        return s.plus(Period.minutes(duration));
    }

    public String getEndMonth() {
        return getEnd().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public String getiCalString() {
        final RRule r = new RRule();

        final ArrayList<WeekdayNum> days = new ArrayList<WeekdayNum>();
        for (int f = 0; f < repeatDays.length; f++) {
            if (repeatDays[f]) {
                days.add(new WeekdayNum(0, Weekday.values()[(f + 1) % 7]));
            }
        }
        r.setByDay(days);
        r.setFreq(Frequency.WEEKLY);
        return r.toIcal();
    }

    public String getNext() {
        return next;
    }

    public Boolean[] getRepeatDays() {
        return repeatDays;
    }

    public String getStartMonth() {
        return this.dateTime.toDateTime().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public void setDateTime(final DateTimeEditDto dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    @JsonIgnore
    public void setiCalString(final String s) {

        Arrays.fill(repeatDays, false);
        if (null == s) {
            recurrent = false;
            return;
        }

        try {
            final RRule rule = new RRule(s);
            for (final WeekdayNum d : rule.getByDay()) {
                repeatDays[d.wday.jsDayNum - 1] = true;
                recurrent = true;
            }

        } catch (final ParseException e) {
            throw new IllegalArgumentException(s, e);
        }

    }

    public void setRecurrent(final boolean recurrent) {
        this.recurrent = recurrent;
    }

    public void setRepeatDays(final Boolean[] repeatDays) {
        this.repeatDays = repeatDays;
    }

}
