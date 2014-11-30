package org.az.skill2peer.nuclei.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "lesson")
@SequenceGenerator(name = "lesson_id_seq", sequenceName = "lesson_id_seq")
public class Lesson extends BaseEntity<Integer> {

    private static final long serialVersionUID = 765205335817537834L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_id_seq")
    private Integer id;

    @Transient
    private Location location;

    @Transient
    private Schedule schedule;

    @Column(name = "description")
    @Size(max = 10000)
    private String description;

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }

}
