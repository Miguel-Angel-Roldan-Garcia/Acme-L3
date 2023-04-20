package acme.features.students.enrolment;

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

    @Autowired
    protected StudentEnrolmentCreateService createService;

    @Autowired
    protected StudentEnrolmentDeleteService deleteService;

    @Autowired
    protected StudentEnrolmentUpdateService updateService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("show", this.showService);
	super.addBasicCommand("create", this.createService);
	super.addBasicCommand("delete", this.deleteService);
	super.addBasicCommand("update", this.updateService);
	super.addCustomCommand("list-mine", "list", this.listMineService);

    }

}
