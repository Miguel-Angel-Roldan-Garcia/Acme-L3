package acme.features.students.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

    @Autowired
    protected StudentLectureRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("masterId", int.class);

	super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Course course;

	masterId = super.getRequest().getData("masterId", int.class);
	course = this.repository.findOneCourseById(masterId);
	status = course != null && !course.isDraftMode();

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Collection<Lecture> objects;
	int masterId;

	masterId = super.getRequest().getData("masterId", int.class);
	objects = this.repository.findManyLecturesByCourseId(masterId);

	super.getBuffer().setData(objects);
    }

    @Override
    public void unbind(final Lecture object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "title", "estimatedLearningTime", "nature", "link");

	super.getResponse().setData(tuple);
    }

}
