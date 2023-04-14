/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.datatypes.TutorialNatureClassification;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select a from Assistant a where a.id = :id")
	Assistant findOneAssistantById(int id);

	@Query("select new acme.datatypes.TutorialNatureClassification(ts.tutorial, case when sum(case when ts.nature = 0 then 1 else 0 end) > sum(case when ts.nature = 1 then 1 else 0 end) then 0 when sum(case when ts.nature = 0 then 1 else 0 end) < sum(case when ts.nature = 1 then 1 else 0 end) then 1 else 2 end) from TutorialSession ts group by ts.tutorial")
	Collection<TutorialNatureClassification> findNatureOfManyTutorials();

	default Map<Nature, Integer> numberOfTutorialsByNature() {
		Collection<TutorialNatureClassification> natureOfTutorials;
		Map<Nature, Integer> numberOfTutorialsByNature;

		natureOfTutorials = this.findNatureOfManyTutorials();
		numberOfTutorialsByNature = new HashMap<Nature, Integer>();
		numberOfTutorialsByNature.put(Nature.THEORETICAL, 0);
		numberOfTutorialsByNature.put(Nature.HANDS_ON, 0);
		numberOfTutorialsByNature.put(Nature.BALANCED, 0);

		for (final TutorialNatureClassification tn : natureOfTutorials)
			numberOfTutorialsByNature.put(tn.getNature(), numberOfTutorialsByNature.get(tn.getNature()) + 1);

		return numberOfTutorialsByNature;
	}

	// Tutorial session statistics --------------------------------------------

	@Query("select avg(TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Double averageTimeOfSessionsByAssistantId(int id);

	@Query("select count(ts) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Integer countOfSessionsByAssistantId(int id);

	@Query("select sqrt(sum(((TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) - :avgTime) * ((TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) - :avgTime))) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Double deviationTimeOfSessionsSubQuery(int id, double avgTime);

	default Double deviationTimeOfSessionsByAssistantId(final int id, final double avgTime, final int sampleSize) {
		return this.deviationTimeOfSessionsSubQuery(id, avgTime) / Math.sqrt(sampleSize);
	}

	@Query("select min(TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Double minimumTimeOfSessionsByAssistantId(int id);

	@Query("select max(TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	Double maximumTimeOfSessionsByAssistantId(int id);

	// Tutorial statistics ----------------------------------------------------


	final String timeOfTutorialById = "select sum(TIME_TO_SEC(TIMEDIFF(ts.endDate, ts.startDate)) / 3600.) from TutorialSession ts where ts.tutorial.id = t.id";


	@Query("select count(t) from Tutorial t where t.assistant.id = :id")
	Integer countOfTutorialsByAssistantId(int id);

	@Query("select avg(" + AssistantDashboardRepository.timeOfTutorialById + ") from Tutorial t where t.assistant.id = :id")
	Double averageTimeOfTutorialsByAssistantId(int id);

	@Query("select sqrt(sum(((" + AssistantDashboardRepository.timeOfTutorialById + ") - :avgTime) * ((" + AssistantDashboardRepository.timeOfTutorialById + ") - :avgTime))) from Tutorial t where t.assistant.id = :id")
	Double deviationTimeOfTutorialsSubQuery(int id, Double avgTime);

	default Double deviationTimeOfTutorialsByAssistantId(final int id, final double avgTime, final int sampleSize) {
		return this.deviationTimeOfTutorialsSubQuery(id, avgTime) / Math.sqrt(sampleSize);
	}

	@Query("select min(" + AssistantDashboardRepository.timeOfTutorialById + ") from Tutorial t where t.assistant.id = :id")
	Double minimumTimeOfTutorialsByAssistantId(int id);

	@Query("select min(" + AssistantDashboardRepository.timeOfTutorialById + ") from Tutorial t where t.assistant.id = :id")
	Double maximumTimeOfTutorialsByAssistantId(int id);

}
