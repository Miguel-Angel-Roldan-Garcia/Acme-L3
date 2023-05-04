
package acme.features.company.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.individual.companies.CompanyDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyDashboardController extends AbstractController<Company, CompanyDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyDashboardShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		//super.addBasicCommand("show", this.showService);
	}

}
