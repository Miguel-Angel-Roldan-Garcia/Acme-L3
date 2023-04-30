/*
 * WorkerApplicationCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureUpdateService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		
		status = super.getRequest().hasData("id", int.class);
		
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CourseLecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;
		int courseId = super.getRequest().getData("course", int.class);
		Course course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
		int lectureId = super.getRequest().getData("lecture", int.class);
		Lecture lecture = this.repository.findOneLectureById(lectureId);
		object.setLecture(lecture);
	}

	@Override
	public void validate(final CourseLecture object) { //custom restrictions
		assert object != null;
		if(!super.getBuffer().getErrors().hasErrors("lecture"))
			super.state(object.getCourse().getLecturer() == object.getLecture().getLecturer(), "*", "lecturer.course-lecture.form.error.not-same-lecturer");
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;
		Tuple tuple = new Tuple();	
		int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		tuple = super.unbind(object, "id");
		Collection<Course> courses = this.repository.findManyCoursesByLecturerId(lecturerId );
		Collection<Lecture> lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		tuple.put("editable", object.getCourse().isDraftMode());
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "title", object.getLecture()));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("editable", object.getCourse().isDraftMode());
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
	}

}
