/*
 * AdministratorOfferUpdateService.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

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
		Offer object;

		object = this.repository.findOneOfferById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStartDate", "availabilityPeriodEndDate", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			boolean moneyAmountStatus;

			moneyAmountStatus = object.getPrice().getAmount() > 0;

			super.state(moneyAmountStatus, "price", "administrator.offer.form.error.price.negative-or-zero");
		}

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			boolean moneyAmountStatus;

			moneyAmountStatus = object.getPrice().getAmount() < 1000000;

			super.state(moneyAmountStatus, "price", "administrator.offer.form.error.price.too-big");
		}

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			boolean moneyCurrencyStatus;
			Collection<String> currencySystemConfiguration;

			currencySystemConfiguration = this.repository.findAllCurrencySystemConfiguration();
			moneyCurrencyStatus = currencySystemConfiguration.contains(object.getPrice().getCurrency());

			super.state(moneyCurrencyStatus, "price", "administrator.offer.form.error.price.non-existent-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStartDate")) {
			boolean startDateStatus;

			startDateStatus = MomentHelper.isAfter(object.getAvailabilityPeriodStartDate(), object.getInstantiationMoment());

			super.state(startDateStatus, "availabilityPeriodStartDate", "administrator.offer.form.error.start-after-instantiation");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStartDate")) {
			boolean startDateStatus;

			startDateStatus = MomentHelper.isLongEnough(object.getInstantiationMoment(), object.getAvailabilityPeriodStartDate(), 1l, ChronoUnit.DAYS);

			super.state(startDateStatus, "availabilityPeriodStartDate", "administrator.offer.form.error.start-after-one-day-of-instantiation");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStartDate")) {
			boolean startDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			startDateStatus = MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodStartDate(), inferiorLimitDate);
			startDateStatus &= MomentHelper.isBeforeOrEqual(object.getAvailabilityPeriodStartDate(), upperLimitDate);

			super.state(startDateStatus, "availabilityPeriodStartDate", "administrator.offer.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodEndDate")) {
			boolean endDateStatus;

			endDateStatus = MomentHelper.isAfter(object.getAvailabilityPeriodEndDate(), object.getAvailabilityPeriodStartDate());

			super.state(endDateStatus, "availabilityPeriodEndDate", "administrator.offer.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodEndDate")) {
			boolean endDateStatus;

			endDateStatus = MomentHelper.isLongEnough(object.getAvailabilityPeriodStartDate(), object.getAvailabilityPeriodEndDate(), 1l, ChronoUnit.WEEKS);

			super.state(endDateStatus, "availabilityPeriodEndDate", "administrator.offer.form.error.at-least-one-week-long");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodEndDate")) {
			boolean endDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			endDateStatus = MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodEndDate(), inferiorLimitDate);
			endDateStatus &= MomentHelper.isBeforeOrEqual(object.getAvailabilityPeriodEndDate(), upperLimitDate);

			super.state(endDateStatus, "availabilityPeriodEndDate", "administrator.offer.form.error.date-out-of-bounds");
		}

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		boolean updateOrDelete;
		Offer offer;
		Date moment;
		moment = MomentHelper.getCurrentMoment();

		offer = this.repository.findActiveOfferById(moment, object.getId());
		updateOrDelete = offer != null ? false : true;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStartDate", "availabilityPeriodEndDate", "price", "link");
		tuple.put("updateOrDelete", updateOrDelete);

		super.getResponse().setData(tuple);
	}

}
