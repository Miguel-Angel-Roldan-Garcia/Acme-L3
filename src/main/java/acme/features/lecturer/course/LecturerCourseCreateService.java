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

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		Course object;
		Lecturer lecturer;
		String currentCurrency;
		Money money;
		
		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		
		currentCurrency = this.repository.findCurrentSystemCurrency();
		money = new Money();
		money.setAmount(0.);
		money.setCurrency(currentCurrency);
		
		object = new Course();
		object.setCode("");
		object.setTitle("");
		object.setAbstract$("");
		object.setRetailPrice(money);
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "abstract$", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) { //custom restrictions
		assert object != null;
		//Unique code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;
			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.duplicated");
		}
		//Money positive
		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			
			super.state(object.getRetailPrice().getAmount() >= 0. 
				|| object.getRetailPrice().getAmount() <1000000, "retailPrice", "lecturer.course.form.error.money-bounds");
		}
		//must be in draft mode at creation
		super.state(object.isDraftMode(), "*", "lecturer.course.form.error.not-draft-mode");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Collection<Lecture> lectures;
		lectures = this.repository.findManyLecturesByCourseId(object.getId());

		int theoreticalCount = 0;
		int handsOnCount = 0;
		for (final Lecture lecture : lectures) {
			if(lecture.getNature().equals(Nature.HANDS_ON)) {
				handsOnCount ++;
			}else {
				theoreticalCount ++;
			}
		}
		Nature nature = theoreticalCount == handsOnCount? Nature.BALANCED : theoreticalCount > handsOnCount ? Nature.THEORETICAL : Nature.HANDS_ON;
		
		boolean publishable = object.isDraftMode() && lectures != null && !lectures.isEmpty();
		tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice","draftMode","link");
		
		tuple.put("nature", nature);
		tuple.put("publishable", publishable);

		super.getResponse().setData(tuple);
	}

}
