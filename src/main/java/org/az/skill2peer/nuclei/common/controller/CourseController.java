package org.az.skill2peer.nuclei.common.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.Urls;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.HasOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PersistenceContext
    EntityManager em;

    @RequestMapping(value = Urls.COURSE_INFO, method = RequestMethod.GET)
    public ModelAndView showCourseDetails(final WebRequest request, final Model model, @RequestParam final Integer id) {
        LOGGER.debug("Rendering  Course Details page.");

        final ModelAndView mv = new ModelAndView(Urls.COURSE_INFO);//XXX: first slash is questionable
        final HasOwner course = em.find(Course.class, id);
        mv.addObject("course", course);
        return mv;
    }

    @RequestMapping(value = Urls.COURSE_CREATE, method = RequestMethod.GET)
    public String showCreateCourseForm(final WebRequest request, final Model model) {
        LOGGER.debug("Rendering CreateCourse  page.");
        return Urls.COURSE_CREATE;
    }

}
