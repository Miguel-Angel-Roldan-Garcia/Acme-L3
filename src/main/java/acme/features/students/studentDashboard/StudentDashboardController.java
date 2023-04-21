package acme.features.students.studentDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.individual.students.StudentDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentDashboardController extends AbstractController<Student, StudentDashboard> {

    @Autowired
    protected StudentDashboardShowService showService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("show", this.showService);
    }

}
