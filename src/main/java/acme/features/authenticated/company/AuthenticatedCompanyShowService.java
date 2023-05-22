/*
 * AuthenticatedCompanyShowService.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyShowService extends AbstractService<Authenticated, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedCompanyRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("username", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Company object;
		String username;

		username = super.getRequest().getData("username", String.class);
		object = this.repository.findOneCompanyByUsername(username);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Company object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "name", "vatNumber", "summary", "link");

		super.getResponse().setData(tuple);
	}

}
