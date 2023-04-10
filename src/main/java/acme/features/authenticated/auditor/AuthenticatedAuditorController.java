
package acme.features.authenticated.auditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuthenticatedAuditorController extends AbstractController<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorCreateService	createService;

	@Autowired
	protected AuthenticatedAuditorUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}
}
