/*
 * CompanyPracticumSessionController.java
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.companies.PracticumSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumSessionController extends AbstractController<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionListService			listService;

	@Autowired
	protected CompanyPracticumSessionShowService			showService;

	@Autowired
	protected CompanyPracticumSessionCreateService			createService;

	@Autowired
	protected CompanyPracticumSessionUpdateService			updateService;

	@Autowired
	protected CompanyPracticumSessionDeleteService			deleteService;

	@Autowired
	protected CompanyPracticumSessionCreateAddendumService	createAddencumService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("create-addendum", "create", this.createAddencumService);
	}

}
