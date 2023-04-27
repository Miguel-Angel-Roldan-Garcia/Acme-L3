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

package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureListService extends AbstractService<Lecturer, CourseLecture> {

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
		Boolean status;
		
		status = super.getRequest().getPrincipal().hasRole("acme.roles.Lecturer");
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CourseLecture> object;
		if(super.getRequest().hasData("courseId")) {
			int courseId = super.getRequest().getData("courseId",int.class);
			object = this.repository.findManyCourseLecturesByCourseId(courseId);
		}else {	
			int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
			object = this.repository.findManyCourseLecturesByLecturerId(lecturerId);
		}

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple ;
		tuple = super.unbind(object,"id");
		tuple.put("editable",object.getCourse().isDraftMode());
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("courseTitle", object.getCourse().getTitle());
		tuple.put("lectureTitle", object.getLecture().getTitle());
		super.getResponse().setData(tuple);
	}

}
