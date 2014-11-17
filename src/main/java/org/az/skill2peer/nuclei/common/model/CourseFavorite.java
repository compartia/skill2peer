package org.az.skill2peer.nuclei.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "course_favorite")
@SequenceGenerator(name = "course_favorite_id_seq", sequenceName = "course_favorite_id_seq")
@NamedQueries({ @NamedQuery(name = "CourseFavorite.findByUserAndCourse", query = "from CourseFavorite where " +
        "userId=:userId " +
        "and courseId=:courseId") })
public class CourseFavorite {

    @Column(name = "course_id")
    private Integer courseId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_favorite_id_seq")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setCourseId(final Integer courseId) {
        this.courseId = courseId;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

}
