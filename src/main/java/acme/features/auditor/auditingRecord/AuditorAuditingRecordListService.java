/*
 * EmployertDutyListService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.auditors.Audit;
import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit Audit;

		masterId = super.getRequest().getData("masterId", int.class);
		Audit = this.repository.findOneAuditById(masterId);
		status = Audit != null && super.getRequest().getPrincipal().hasRole(Audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyAuditingRecordsByMasterId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "startDate", "finishDate", "link", "mark");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> objects) {
		assert objects != null;

		int masterId;
		Audit Audit;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		Audit = this.repository.findOneAuditById(masterId);
		showCreate = Audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(Audit.getAuditor());

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
