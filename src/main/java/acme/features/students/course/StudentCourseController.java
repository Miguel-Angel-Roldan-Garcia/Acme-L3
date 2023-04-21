package acme.features.students.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.lectures.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentCourseController extends AbstractController<Student, Course> {

    @Autowired
    protected StudentCourseListService listService;

    @Autowired
    protected StudentCourseShowService showService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("list", this.listService);
	super.addBasicCommand("show", this.showService);
    }

}