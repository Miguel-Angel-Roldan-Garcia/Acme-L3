/*
 * AuditorAuditingRecordUpdateService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.individual.auditors.Audit;
import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

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
		int AuditingRecordId;
		Audit Audit;
		AuditingRecord AuditingRecord;

		AuditingRecordId = super.getRequest().getData("id", int.class);
		AuditingRecord = this.repository.findOneAuditingRecordById(AuditingRecordId);
		Audit = this.repository.findOneAuditByAuditRecordId(AuditingRecordId);
		status = Audit != null && super.getRequest().getPrincipal().hasRole(Audit.getAuditor()) && AuditingRecord.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "startDate", "finishDate", "link");
		object.setMark(Mark.transform(mark));
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			boolean endDateError;

			endDateError = MomentHelper.isBefore(object.getStartDate(), object.getFinishDate());

			super.state(endDateError, "finishDate", "auditor.auditing-record.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(!(object.getDurationInHours() <= 1), "finishDate", "auditor.auditing-record.form.error.duration");
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		SelectChoices marks;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "startDate", "finishDate", "link", "mark");
		tuple.put("mark", marks.getSelected().getKey());
		tuple.put("marks", marks);
		tuple.put("masterId", object.getAudit().getId());

		super.getResponse().setData(tuple);
	}

}
