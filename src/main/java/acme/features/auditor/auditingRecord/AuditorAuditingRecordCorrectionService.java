
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;

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
public class AuditorAuditingRecordCorrectionService extends AbstractService<Auditor, AuditingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final Audit audit = this.repository.findOneAuditById(super.getRequest().getData("auditId", int.class));
		status = super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && !audit.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;

		object = new AuditingRecord();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final int auditId = super.getRequest().getData("auditId", int.class);
		final Audit audit = this.repository.findOneAuditById(auditId);
		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "link", "startDate", "finishDate");
		object.setAudit(audit);
		object.setMark(Mark.transform(mark));
		object.setDraftMode(false);
		object.setCorrection(true);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			boolean endDateErrorDuration;

			endDateErrorDuration = MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), 1l, ChronoUnit.HOURS);

			if (endDateErrorDuration)
				endDateErrorDuration = !MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), (long) 3600 + 1, ChronoUnit.SECONDS);

			super.state(endDateErrorDuration, "finishDate", "auditor.auditing-record.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			boolean endDateError;

			endDateError = MomentHelper.isBefore(object.getStartDate(), object.getFinishDate());

			super.state(endDateError, "finishDate", "auditor.auditing-record.form.error.end-before-start");
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

		final Tuple tuple;
		final SelectChoices marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "link", "mark", "startDate", "finishDate");
		tuple.put("marks", marks);
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));

		super.getResponse().setData(tuple);
	}
}
