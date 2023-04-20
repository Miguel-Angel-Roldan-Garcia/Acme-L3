/*
 * WorkerApplicationCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.auditors.Audit;
import acme.entities.individual.lectures.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", String.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		Auditor auditor;
		Course course;

		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		course = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class));

		object = new Audit();
		object.setConclusion("");
		object.setStrongPoints("");
		object.setWeakPoints("");
		object.setCode("");
		object.setDraftMode(true);
		object.setCourse(course);
		object.setAuditor(auditor);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "strongPoints", "weakPoints", "conclusion");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;
			existing = this.repository.findOneAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.audit.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "strongPoints", "weakPoints", "conclusion", "draftMode");
		tuple.put("courseCode", this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class)).getCode());

		super.getResponse().setData(tuple);
	}

}
