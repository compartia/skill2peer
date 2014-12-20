package org.az.skill2peer.nuclei.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

@Entity
@Table(name = "schedule")
@SequenceGenerator(name = "schedule_id_seq", sequenceName = "schedule_id_seq")
public class Schedule extends BaseEntity<Integer> {

    private static final long serialVersionUID = 4873701983319765707L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_seq")
    private Integer id;

    @Column(name = "start_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime start;

    @Column(name = "end_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime end;

    @Column(name = "repeat")
    private String iCalString;

    //    @Column(name = "duration")
    //    private Integer duration;

    public Integer getDuration() {
        if (start == null || end == null) {
            return null;
        }
        //        final Period p = new Period(start, end);
        //        return p.toStandardMinutes().getMinutes();
        //
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    /* methods */

    public DateTime getEnd() {
        return end;
    }

    public String getiCalString() {
        return iCalString;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public DateTime getNextEvent() {
        return CalendarUtils.getNextEvent(this);
    }

    public DateTime getStart() {
        return start;
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setiCalString(final String iCalString) {
        this.iCalString = iCalString;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }
}
