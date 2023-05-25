/*
 * AuthenticatedAuditorShowService.java
 *
 * Copyright (C) 2022-2023 Álvaro Urquijo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorShowService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditorRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("username", String.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Auditor object;
		String username;

		username = super.getRequest().getData("username", String.class);
		object = this.repository.findOneAuditorByUsername(username);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Auditor object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "firm", "professionalId", "certifications", "link");

		super.getResponse().setData(tuple);
	}

}
