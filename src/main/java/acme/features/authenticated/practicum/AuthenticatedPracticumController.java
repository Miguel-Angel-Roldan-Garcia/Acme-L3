/*
 * AuthenticatedPracticumController.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.companies.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPracticumController extends AbstractController<Authenticated, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPracticumListService	listService;

	@Autowired
	protected AuthenticatedPracticumShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
