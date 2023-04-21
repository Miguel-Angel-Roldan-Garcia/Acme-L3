/*
 * WorkerApplicationShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.entities.individual.lectures.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		status = tutorial != null && super.getRequest().getPrincipal().getActiveRoleId() == tutorial.getAssistant().getId();

		super.getResponse().setAuthorised(status);
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
		SelectChoices choices;
		Collection<Course> courses;

		courses = this.repository.findManyPublishedCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tutorialSessions = this.repository.findManySessionsByTutorialId(object.getId());
		estimatedTotalTime = 0.;

		for (final TutorialSession ts : tutorialSessions)
			estimatedTotalTime += ts.getDurationInHours();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "assistant", "draftMode");
		tuple.put("estimatedTotalTime", estimatedTotalTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
