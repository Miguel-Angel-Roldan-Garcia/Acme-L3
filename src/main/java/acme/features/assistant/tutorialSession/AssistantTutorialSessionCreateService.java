/*
 * AssistantTutorialSessionCreateService.java
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

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

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
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);

		object = new TutorialSession();
		object.setTitle("");
		object.setAbstract$("");
		object.setNature(Nature.THEORETICAL);
		object.setStartDate(MomentHelper.getCurrentMoment());
		object.setEndDate(MomentHelper.getCurrentMoment());
		object.setLink("");
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "nature", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startDateError;

			startDateError = MomentHelper.isAfter(object.getStartDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.DAYS));

			super.state(startDateError, "startDate", "assistant.tutorial-session.form.error.at-least-one-day-ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean startDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			startDateStatus = MomentHelper.isAfterOrEqual(object.getStartDate(), inferiorLimitDate);
			startDateStatus &= MomentHelper.isBeforeOrEqual(object.getStartDate(), upperLimitDate);

			super.state(startDateStatus, "startDate", "assistant.tutorial-session.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateError;

			endDateError = MomentHelper.isBefore(object.getStartDate(), object.getEndDate());

			super.state(endDateError, "endDate", "assistant.tutorial-session.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateErrorDuration;

			endDateErrorDuration = MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1l, ChronoUnit.HOURS);

			if (endDateErrorDuration)
				endDateErrorDuration = !MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), (long) 5 * 3600 + 1, ChronoUnit.SECONDS);

			super.state(endDateErrorDuration, "endDate", "assistant.tutorial-session.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean endDateStatus;
			Date inferiorLimitDate;
			Date upperLimitDate;

			inferiorLimitDate = new Date(946681200000l); // HINT This is Jan 1 2000 at 00:00
			upperLimitDate = new Date(4133977140000l); // HINT This is Dec 31 2100 at 23:59

			endDateStatus = MomentHelper.isAfterOrEqual(object.getEndDate(), inferiorLimitDate);
			endDateStatus &= MomentHelper.isBeforeOrEqual(object.getEndDate(), upperLimitDate);

			super.state(endDateStatus, "endDate", "assistant.tutorial-session.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("nature"))
			super.state(!object.getNature().equals(Nature.BALANCED), "nature", "assistant.tutorial-session.form.error.nature-balanced");

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		int masterId;
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Nature.class, object.getNature());

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "abstract$", "nature", "startDate", "endDate", "link");
		tuple.put("masterId", masterId);
		tuple.put("natures", choices);

		super.getResponse().setData(tuple);
	}

}
