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

package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

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
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<TutorialSession> tutorialSessions;
		Double estimatedTotalTime;

		tutorialSessions = this.repository.findManySessionsByTutorialId(object.getId());
		estimatedTotalTime = 0.;

		for (final TutorialSession ts : tutorialSessions)
			estimatedTotalTime += ts.getDurationInHours();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "assistant");
		tuple.put("courseCode", this.repository.findCourseCodeByTutorialId(object.getId()));
		tuple.put("estimatedTotalTime", estimatedTotalTime);

		super.getResponse().setData(tuple);
	}

}
