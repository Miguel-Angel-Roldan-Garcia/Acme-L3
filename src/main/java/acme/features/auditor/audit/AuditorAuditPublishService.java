
package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.auditors.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

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
		int AuditId;
		Audit Audit;
		Auditor Auditor;

		AuditId = super.getRequest().getData("id", int.class);
		Audit = this.repository.findOneAuditById(AuditId);
		Auditor = Audit == null ? null : Audit.getAuditor();
		status = Audit != null && Audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(Auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("courseCode", this.repository.findCourseCodeByAuditId(object.getId()));

		super.getResponse().setData(tuple);
	}
}
