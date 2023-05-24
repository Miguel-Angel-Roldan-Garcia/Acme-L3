/*
 * AuthenticatedCompanyRepository.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("select company from Company company where company.userAccount.id = :id")
	Company findOneCompanyByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select company from Company company where company.vatNumber = :vatNumber")
	Company findOneCompanyByVatNumber(String vatNumber);

	@Query("select c from Company c where c.userAccount.username = :username")
	Company findOneCompanyByUsername(String username);

}
