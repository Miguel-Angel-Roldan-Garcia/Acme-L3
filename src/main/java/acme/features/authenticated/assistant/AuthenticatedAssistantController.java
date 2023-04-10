
package acme.features.authenticated.assistant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AuthenticatedAssistantController extends AbstractController<Authenticated, Assistant> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAssistantCreateService	createService;

	@Autowired
	protected AuthenticatedAssistantUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
