/*
 * AssistantTutorialSessionListService.java
 *
 * Copyright (C) 2022-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

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
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyTutorialSessionsByMasterId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "nature", "startDate", "endDate", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<TutorialSession> objects) {
		assert objects != null;

		int masterId;
		Tutorial tutorial;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setGlobal("masterId", masterId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
