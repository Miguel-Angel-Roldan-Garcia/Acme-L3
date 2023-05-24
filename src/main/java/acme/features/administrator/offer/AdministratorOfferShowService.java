/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Offer;
import acme.forms.group.MoneyExchange;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.helpers.MoneyExchangeHelper;

@Service
public class AdministratorOfferShowService extends AbstractService<Administrator, Offer> {

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
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		boolean updateOrDelete;
		Offer offer;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		Tuple tuple;
		MoneyExchange exchange;

		offer = this.repository.findActiveOfferById(moment, object.getId());
		updateOrDelete = offer != null ? false : true;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStartDate", "availabilityPeriodEndDate", "price", "link");
		tuple.put("updateOrDelete", updateOrDelete);

		// HINT: Try catch so that if any errors occurred, the normal functionality is not 
		// HINT+ stopped by this secondary feature.
		try {
			exchange = MoneyExchangeHelper.computeMoneyExchange(object.getPrice(), this.repository.findSystemCurrency());
			tuple.put("moneyExchange", exchange);
		} catch (final Exception e) {
			tuple.put("moneyExchange", null);
		}

		super.getResponse().setData(tuple);
	}

}
