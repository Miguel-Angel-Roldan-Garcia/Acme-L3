
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AuthenticatedBulletinRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Collection<Bulletin> object;

		object = this.repository.findAllBulletins();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "title", "message", "critical", "link");

		super.getResponse().setData(tuple);
	}

}
