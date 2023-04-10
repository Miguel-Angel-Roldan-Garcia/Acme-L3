package acme.features.students.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.students.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

    @Autowired
    protected StudentActivityListService listService;

}
