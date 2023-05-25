/*
 * AdministratorOfferController.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.group.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorOfferController extends AbstractController<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferListAllService	listAllService;

	@Autowired
	protected AdministratorOfferShowService		showService;

	@Autowired
	protected AdministratorOfferCreateService	createService;

	@Autowired
	protected AdministratorOfferUpdateService	updateService;

	@Autowired
	protected AdministratorOfferDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
