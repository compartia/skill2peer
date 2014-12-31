package org.az.skill2peer.nuclei.common.controller;

import org.az.skill2peer.nuclei.common.Urls;
import org.az.skill2peer.nuclei.common.controller.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.PriceDto;
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
        course.setSkills("fake skill 1,fake skill 2,fake skill 3");
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

}
