package org.az.skill2peer.nuclei.common.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * a course consists of lessons
 *
 * @author Artem Zaborskiy
 *
 */
@Entity
@Table(name = "lesson")
@SequenceGenerator(name = "lesson_id_seq", sequenceName = "lesson_id_seq")
public class Lesson extends BaseEntity<Integer> {

    private static final long serialVersionUID = 765205335817537834L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_id_seq")
    private Integer id;

    @Valid
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "description")
    @Size(max = 10000)
    private String description;

    /*    methods   */

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
