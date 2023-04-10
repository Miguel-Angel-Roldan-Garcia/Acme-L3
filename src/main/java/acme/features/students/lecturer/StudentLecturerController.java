package acme.features.students.lecturer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;
import acme.roles.Student;

@Controller
public class StudentLecturerController extends AbstractController<Student, Lecturer> {

    @Autowired
    protected StudentLecturerShowService showService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("show", this.showService);
    }

}
