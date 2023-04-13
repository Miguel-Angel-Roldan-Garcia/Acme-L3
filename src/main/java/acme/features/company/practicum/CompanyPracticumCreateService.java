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

package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.entities.individual.lectures.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ----------------------------------------------

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", String.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Practicum object;
		Company company;
		Course course;

		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		course = this.repository.findOneCourseByCode(super.getRequest().getData("courseId", String.class));

		object = new Practicum();
		object.setTitle("");
		object.setAbstract$("");
		object.setGoals("");
		object.setCode("");
		object.setDraftMode(true);
		object.setCourse(course);
		object.setCompany(company);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "goals");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;
			existing = this.repository.findOnePracticumByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "company.practicum.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		Collection<PracticumSession> practicumSessions;
		Double estimatedTotalTime;

		practicumSessions = this.repository.findManyPracticumSessionsByPracticumId(object.getId());
		estimatedTotalTime = 0.;

		for (final PracticumSession ps : practicumSessions)
			estimatedTotalTime += ps.getDurationInHours();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "draftMode");
		tuple.put("courseCode",
				this.repository.findOneCourseByCode(super.getRequest().getData("courseId", String.class)).getCode());
		tuple.put("estimatedTotalTime", estimatedTotalTime);

		super.getResponse().setData(tuple);
	}

}
