/*
 * AssistantDashboardShowService.java
 *
 * Copyright (C) 2022-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.dashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.forms.individual.assistants.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int assistantId;
		AssistantDashboard dashboard;
		Map<Nature, Integer> tutorialCount;
		Statistic sessionTimeStatistics;
		Statistic tutorialTimeStatistics;

		assistantId = super.getRequest().getPrincipal().getActiveRoleId();

		tutorialCount = this.repository.numberOfTutorialsByNature();

		sessionTimeStatistics = new Statistic();
		sessionTimeStatistics.setCount(this.repository.countOfSessionsByAssistantId(assistantId));
		sessionTimeStatistics.setAverageValue(this.repository.averageTimeOfSessionsByAssistantId(assistantId));
		sessionTimeStatistics.setDeviationValue(this.repository.deviationTimeOfSessionsByAssistantId(assistantId, sessionTimeStatistics.getAverageValue(), sessionTimeStatistics.getCount()));
		sessionTimeStatistics.setMinValue(this.repository.minimumTimeOfSessionsByAssistantId(assistantId));
		sessionTimeStatistics.setMaxValue(this.repository.maximumTimeOfSessionsByAssistantId(assistantId));

		tutorialTimeStatistics = new Statistic();
		tutorialTimeStatistics.setAverageValue(0.);
		tutorialTimeStatistics.setDeviationValue(0.);
		tutorialTimeStatistics.setMaxValue(0.);
		tutorialTimeStatistics.setMinValue(0.);
		tutorialTimeStatistics.setCount(this.repository.countOfTutorialsByAssistantId(assistantId));
		tutorialTimeStatistics.setAverageValue(this.repository.averageTimeOfTutorialsByAssistantId(assistantId));
		tutorialTimeStatistics.setDeviationValue(this.repository.deviationTimeOfTutorialsByAssistantId(assistantId, tutorialTimeStatistics.getAverageValue(), tutorialTimeStatistics.getCount()));
		tutorialTimeStatistics.setMinValue(this.repository.minimumTimeOfTutorialsByAssistantId(assistantId));
		tutorialTimeStatistics.setMaxValue(this.repository.maximumTimeOfTutorialsByAssistantId(assistantId));

		dashboard = new AssistantDashboard();

		dashboard.setTutorialCount(tutorialCount);
		dashboard.setSessionTimeStatistics(sessionTimeStatistics);
		dashboard.setTutorialTimeStatistics(tutorialTimeStatistics);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "tutorialCount", "sessionTimeStatistics", "tutorialTimeStatistics");

		super.getResponse().setData(tuple);
	}

}
