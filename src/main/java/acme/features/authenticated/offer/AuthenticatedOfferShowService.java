/*
 * AuthenticatedOfferShowService.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Offer;
import acme.forms.group.MoneyExchange;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.helpers.MoneyExchangeHelper;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository repository;

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
		int offerId;
		Offer offer;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(offerId);
		status = offer != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
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

		Tuple tuple;
		MoneyExchange exchange;

		tuple = super.unbind(object, "heading", "summary", "price", "link", "availabilityPeriodStartDate", "availabilityPeriodEndDate");


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
