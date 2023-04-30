/*
 * EmployerJobDeleteService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;
		Lecturer lecturer;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = lecture != null && lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "code", "title", "abstract$", "retailPrice");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if(!super.getBuffer().getErrors().hasErrors("nature")) {
			super.state(object.getNature() != Nature.BALANCED, "nature", "lecturer.lecture.form.error.nature-balanced");
		}
		super.state(object.isDraftMode(), "*", "lecturer.lecture.form.error.not-draft-mode");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		Collection<CourseLecture> courseLectures;

		courseLectures = this.repository.findManyCourseLecturesByLectureId(object.getId());
		this.repository.deleteAll(courseLectures);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice", "draftMode");


		super.getResponse().setData(tuple);
	}

}
