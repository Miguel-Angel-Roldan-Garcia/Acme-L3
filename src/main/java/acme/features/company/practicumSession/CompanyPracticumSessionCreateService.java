/*
 * EmployerTutorialSessionCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.companies.Practicum;
import acme.entities.individual.companies.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

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
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);

		object = new PracticumSession();
		object.setTitle("");
		object.setAbstract$("");
		object.setStartDate(MomentHelper.getCurrentMoment());
		object.setEndDate(MomentHelper.getCurrentMoment());
		object.setLink("");
		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startDateError;

			startDateError = MomentHelper.isAfter(object.getStartDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.WEEKS));

			super.state(startDateError, "startDate", "company.practicum-session.form.error.at-least-one-week-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			startDateStatus = MomentHelper.isAfterOrEqual(object.getStartDate(), inferiorLimitDate);
			startDateStatus &= MomentHelper.isBeforeOrEqual(object.getStartDate(), upperLimitDate);

			super.state(startDateStatus, "startDate", "company.practicum-session.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateError;

			endDateError = MomentHelper.isBefore(object.getStartDate(), object.getEndDate());

			super.state(endDateError, "endDate", "company.practicum-session.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateErrorDuration;

			endDateErrorDuration = !MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1l, ChronoUnit.WEEKS);

			super.state(endDateErrorDuration, "endDate", "company.practicum-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			endDateStatus = MomentHelper.isAfterOrEqual(object.getEndDate(), inferiorLimitDate);
			endDateStatus &= MomentHelper.isBeforeOrEqual(object.getEndDate(), upperLimitDate);

			super.state(endDateStatus, "endDate", "company.practicum-session.form.error.date-out-of-bounds");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		int masterId;
		Tuple tuple;

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "abstract$", "startDate", "endDate", "link");
		tuple.put("masterId", masterId);

		super.getResponse().setData(tuple);
	}

}
