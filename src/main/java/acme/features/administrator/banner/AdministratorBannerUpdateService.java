/*
 * EmployerJobUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;

		object = this.repository.findOneBannerById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "displayPeriodStartDate", "displayPeriodEndDate", "pictureLink", "slogan", "targetWebDocumentLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodStartDate")) {
			boolean startDateStatus;

			startDateStatus = MomentHelper.isAfter(object.getDisplayPeriodStartDate(), object.getInstantiationMoment());

			super.state(startDateStatus, "displayPeriodStartDate", "administrator.banner.form.error.start-after-instantiation");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodStartDate")) {
			boolean startDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			startDateStatus = MomentHelper.isAfterOrEqual(object.getDisplayPeriodStartDate(), inferiorLimitDate);
			startDateStatus &= MomentHelper.isBeforeOrEqual(object.getDisplayPeriodStartDate(), upperLimitDate);

			super.state(startDateStatus, "displayPeriodStartDate", "administrator.banner.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEndDate")) {
			boolean endDateStatus;

			endDateStatus = MomentHelper.isAfter(object.getDisplayPeriodEndDate(), object.getDisplayPeriodStartDate());

			super.state(endDateStatus, "displayPeriodEndDate", "administrator.banner.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEndDate")) {
			boolean endDateStatus;

			endDateStatus = MomentHelper.isLongEnough(object.getDisplayPeriodStartDate(), object.getDisplayPeriodEndDate(), 1l, ChronoUnit.WEEKS);

			super.state(endDateStatus, "displayPeriodEndDate", "administrator.banner.form.error.at-least-one-week-long");
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEndDate")) {
			boolean endDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			endDateStatus = MomentHelper.isAfterOrEqual(object.getDisplayPeriodEndDate(), inferiorLimitDate);
			endDateStatus &= MomentHelper.isBeforeOrEqual(object.getDisplayPeriodEndDate(), upperLimitDate);

			super.state(endDateStatus, "displayPeriodEndDate", "administrator.banner.form.error.date-out-of-bounds");
		}

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "displayPeriodStartDate", "displayPeriodEndDate", "pictureLink", "slogan", "targetWebDocumentLink");

		super.getResponse().setData(tuple);
	}

}
