
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.individual.auditors.AuditingRecord;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordPublishService extends AbstractService<Auditor, AuditingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final AuditingRecord ar = this.repository.findOneAuditingRecordById(super.getRequest().getData("id", int.class));
		status = super.getRequest().getPrincipal().hasRole(ar.getAudit().getAuditor()) && ar.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;

		object = this.repository.findOneAuditingRecordById(super.getRequest().getData("id", int.class));
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "link", "startDate", "finishDate", "draftMode");
		object.setMark(Mark.transform(mark));
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
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		final Tuple tuple;
		final SelectChoices marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "link", "mark", "startDate", "finishDate", "draftMode");
		tuple.put("marks", marks);

		super.getResponse().setData(tuple);
	}
}
