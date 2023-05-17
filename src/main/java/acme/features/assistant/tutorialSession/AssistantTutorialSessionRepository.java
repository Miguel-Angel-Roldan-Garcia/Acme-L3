/*
 * AssistantTutorialSessionRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select ts.tutorial from TutorialSession ts where ts.id = :id")
	Tutorial findOneTutorialByTutorialSessionId(int id);

	@Query("select ts from TutorialSession ts where ts.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :masterId")
	Collection<TutorialSession> findManyTutorialSessionsByMasterId(int masterId);

}
