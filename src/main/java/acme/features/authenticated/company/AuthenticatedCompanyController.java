
package acme.features.authenticated.company;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class AuthenticatedCompanyController extends AbstractController<Authenticated, Company> {

	@Autowired
	protected AuthenticatedCompanyCreateService	createService;

	@Autowired
	protected AuthenticatedCompanyUpdateService	updateService;

	@Autowired
	protected AuthenticatedCompanyShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("show", this.showService);

	}

}
