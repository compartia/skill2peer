package org.az.skill2peer.nuclei.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.az.skill2peer.nuclei.user.model.User;

@Entity
@Table(name = "enrollment")
@NamedQueries({ @NamedQuery(name = "Enrollment.findByCourseId", query = "from Enrollment where "
        + "course.id=:courseId") })
//@SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq")
public class Enrollment implements HasIntegerId, Serializable {

    private static final long serialVersionUID = -9179199550086414556L;

    @Id
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Course.class)
    @JoinColumn(name = "course_id")
    private Course course;

    public Course getCourse() {
        return course;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}
