package org.az.skill2peer.nuclei.common.controller;

import java.util.Date;

import org.az.skill2peer.nuclei.Urls;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.PriceDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleDto;
import org.az.skill2peer.nuclei.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @RequestMapping(value = Urls.COURSE_INFO, method = RequestMethod.GET)
    public ModelAndView showCourseDetails(final WebRequest request, final Model model, @RequestParam final Integer id) {
        LOGGER.debug("Rendering  Course Details page.");

        final ModelAndView mv = new ModelAndView(Urls.COURSE_INFO);//XXX: first slash is questionable
        final CourseInfoDto course = courseService.getCourseFullInfo(id);
        fakeCourseData(course);
        mv.addObject("course", course);
        return mv;
    }

    @RequestMapping(value = Urls.COURSE_CREATE, method = RequestMethod.GET)
    public String showCreateCourseForm(final WebRequest request, final Model model) {
        LOGGER.debug("Rendering CreateCourse  page.");
        return Urls.COURSE_CREATE;
    }

    /**
     * XXX: remove this when data structures are done
     * @param course
     */

    @Deprecated
    private void fakeCourseData(final CourseInfoDto course) {
        course.setLocation(fakeLocation());
        course.setPrice(fakePrice());
        course.setSchedule(fakeSchedule());

        course.getLessons().add(fakeLesson(1));
        course.getLessons().add(fakeLesson(2));
        course.getLessons().add(fakeLesson(3));
        course.getLessons().add(fakeLesson(4));
        course.getLessons().add(fakeLesson(5));

        course.setSkills("fake skill 1,fake skill 2,fake skill 3");

    }

    @Deprecated
    private LessonInfoDto fakeLesson(final int no) {
        final LessonInfoDto lesson = new LessonInfoDto();
        lesson.setDescription("lesson descr " + no);
        lesson.setId(no);
        lesson.setLocation(fakeLocation());
        lesson.setName("lesson name " + no);
        lesson.setPrice(fakePrice());
        lesson.setSchedule(fakeSchedule());
        lesson.setSkills("lesson skill " + no);
        lesson.setSummary("lesson summary " + no);
        return lesson;
    }

    @Deprecated
    private LocationDto fakeLocation() {
        final LocationDto location = new LocationDto();
        location.setId(12);
        location.setAddress("Петербург, Петра Лаврова улица 45, кв 193");
        location.setName("Студия ArtLab");
        return location;
    }

    @Deprecated
    private PriceDto fakePrice() {
        final PriceDto price = new PriceDto();
        price.setComment("Стоимость включает все расходные материалы");
        price.setCurrency("RUB");
        price.setValue("1500");
        return price;
    }

    @Deprecated
    private ScheduleDto fakeSchedule() {
        final ScheduleDto schedule = new ScheduleDto();
        schedule.setDuration(120);
        schedule.setStart(new Date());
        return schedule;
    }
}
