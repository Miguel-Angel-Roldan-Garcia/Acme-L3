
package acme.features.administrator.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.group.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorBulletinController extends AbstractController<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinCreateService createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}

}
