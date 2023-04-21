
package acme.features.authenticated.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.group.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedBulletinController extends AbstractController<Authenticated, Bulletin> {

	//Internal state -------------------------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinListService	listService;

	@Autowired
	AuthenticatedBulletinShowService			showService;

	//Constructor


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
