package org.az.skill2peer.nuclei.services;

import org.springframework.security.access.annotation.Secured;

public interface CourseAdminService extends CourseService {

    @Secured({ "ROLE_ADMIN" })
    Integer publishCourse(Integer courseId);
}
