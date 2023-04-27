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
public class LecturerCourseLectureCreateService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CourseLecture object;
		object = new CourseLecture();
		object.setCourse(new Course());
		object.setLecture(new Lecture());

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
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;
		int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Course> courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		Collection<Lecture> lectures = this.repository.findManyLecturesByLecturerId(lecturerId);;
		Tuple tuple = new Tuple();
		tuple.put("courses", SelectChoices.from(courses, "code", null));
		tuple.put("lectures", SelectChoices.from(lectures, "title", null));
		super.getResponse().setData(tuple);
	}

}
