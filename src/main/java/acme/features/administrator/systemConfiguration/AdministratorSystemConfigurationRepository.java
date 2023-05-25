/*
 * AdministratorSystemConfigurationRepository.java
 *
 * Copyright (C) 2022-2023 Miguel Ángel Roldán.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.systemConfiguration;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.group.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	Collection<SystemConfiguration> findManySystemConfigurations();

	default SystemConfiguration findSystemConfiguration() {
		return this.findManySystemConfigurations().stream().findFirst().orElse(null);
	}

}
