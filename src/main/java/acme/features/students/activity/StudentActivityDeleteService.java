package acme.features.students.activity;

import org.springframework.stereotype.Service;

import acme.entities.individual.students.Activity;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

}
