/*
 * WorkerApplicationListService.java
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

import acme.entities.individual.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		Boolean status;
		
		status = super.getRequest().getPrincipal().hasRole("acme.roles.Lecturer");
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> object;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		if(super.getRequest().hasData("courseId", int.class)) {
			Integer courseId = super.getRequest().getData("courseId", int.class);
			object = this.repository.findManyLecturesByCourseId(courseId);
		}else {
			object = this.repository.findManyLecturesByLecturerId(lecturerId);
			
		}
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Boolean showCreate;
		Tuple tuple = new Tuple();

		tuple = super.unbind(object, "title", "estimatedLearningTime", "nature");
		showCreate =(super.getRequest().hasData("courseId",int.class))? false : true;
		super.getResponse().setGlobal("showCreate", showCreate);

		super.getResponse().setData(tuple);
	}

}
