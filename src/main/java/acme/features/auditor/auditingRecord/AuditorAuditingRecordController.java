/*
 * AuditorAuditingRecordController.java
 *
 * Copyright (C) 2022-2023 Álvaro Urquijo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditingRecordController extends AbstractController<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordListService			listService;

	@Autowired
	protected AuditorAuditingRecordShowService			showService;

	@Autowired
	protected AuditorAuditingRecordCreateService		createService;

	@Autowired
	protected AuditorAuditingRecordUpdateService		updateService;

	@Autowired
	protected AuditorAuditingRecordDeleteService		deleteService;

	@Autowired
	protected AuditorAuditingRecordPublishService		publishService;

	@Autowired
	protected AuditorAuditingRecordCorrectionService	correctionService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("correct", "create", this.correctionService);
	}

}
