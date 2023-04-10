
package acme.features.authenticated.assistant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AuthenticatedAssistantRepository extends AbstractRepository {

	@Query("select a from Assistant a where a.userAccount.id = :id")
	Assistant findOneAssistantByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
