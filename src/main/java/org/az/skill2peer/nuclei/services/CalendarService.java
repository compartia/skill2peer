package org.az.skill2peer.nuclei.services;

import java.util.List;

import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.UserInfoDto;

public interface CalendarService {

    List<UserInfoDto> getEnrolledUsers(Integer courseId);

    List<EventDto> getEvents(Integer userId);
}
