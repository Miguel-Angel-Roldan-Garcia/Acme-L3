/*
 * EmployerAuditingRecordUpdateService.java
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

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.auditors.Audit;
import acme.entities.individual.auditors.AuditingRecord;
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

		AuditingRecordId = super.getRequest().getData("id", int.class);
		Audit = this.repository.findOneAuditByAuditRecordId(AuditingRecordId);
		status = Audit != null && super.getRequest().getPrincipal().hasRole(Audit.getAuditor());

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

		super.bind(object, "subject", "assessment", "startDate", "finishDate", "link", "mark");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startDateError;

			startDateError = MomentHelper.isBefore(object.getStartDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.DAYS));

			super.state(startDateError, "startDate", "assistant.tutorial-session.form.error.at-least-one-day-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			boolean endDateErrorDuration;

			endDateErrorDuration = MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), 1l, ChronoUnit.HOURS);

			if (endDateErrorDuration)
				endDateErrorDuration = !MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), (long) 3600 + 1, ChronoUnit.SECONDS);

			super.state(endDateErrorDuration, "finishDate", "assistant.tutorial-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			boolean endDateError;

			endDateError = MomentHelper.isBefore(object.getStartDate(), object.getFinishDate());

			super.state(endDateError, "finishDate", "assistant.tutorial-session.form.error.end-before-start");
		}
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

		tuple = super.unbind(object, "subject", "assessment", "startDate", "finishDate", "link", "mark");
		tuple.put("masterId", object.getAudit().getId());

		super.getResponse().setData(tuple);
	}

}
