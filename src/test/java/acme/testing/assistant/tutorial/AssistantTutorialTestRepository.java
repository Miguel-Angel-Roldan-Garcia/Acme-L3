/*
 * AssistantTutorialTestRepository.java
 *
 * Copyright (C) 2022-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(String username);

	@Query("select ts from TutorialSession ts where ts.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findManyDutiesByEmployerUsername(String username);

}
