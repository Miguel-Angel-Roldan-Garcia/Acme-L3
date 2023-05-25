/*
 * CompanyPracticumSessionDeleteService.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

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
		int practicumSessionId;
		Practicum practicum;
		PracticumSession practicumSession;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumByPracticumSessionId(practicumSessionId);
		practicumSession = this.repository.findOnePracticumSessionById(practicumSessionId);
		status = practicum != null && practicumSession.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startDate", "endDate", "link", "isAddendum");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startDate", "endDate", "link", "isAddendum");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}

}
