
package acme.features.authenticated.student;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class AuthenticatedStudentController extends AbstractController<Authenticated, Student> {

	@Autowired
	protected AuthenticatedStudentCreateService	createService;

	@Autowired
	protected AuthenticatedStudentUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}

}
