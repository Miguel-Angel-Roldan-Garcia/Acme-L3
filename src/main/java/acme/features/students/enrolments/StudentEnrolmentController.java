package acme.features.students.enrolments;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.students.Enrolment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentEnrolmentController extends AbstractController<Student, Enrolment> {

    @Autowired
    protected StudentEnrolmentListMineService listMineService;

    @Autowired
    protected StudentEnrolmentShowService showService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("show", this.showService);
	super.addBasicCommand("create", this.createService);

	super.addCustomCommand("list-mine", "list", this.listMineService);

    }

}
