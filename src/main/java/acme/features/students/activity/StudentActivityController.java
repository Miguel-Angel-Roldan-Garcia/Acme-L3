package acme.features.students.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.students.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

    @Autowired
    protected StudentActivityListService listService;

    @Autowired
    protected StudentActivityCreateService createService;

    @Autowired
    protected StudentActivityShowService showService;

    @Autowired
    protected StudentActivityDeleteService deleteService;

    @PostConstruct
    protected void initialise() {
	super.addBasicCommand("list", this.listService);
	super.addBasicCommand("create", this.createService);

    }

}
