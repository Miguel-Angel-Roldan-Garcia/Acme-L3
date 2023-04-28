package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Peep;
import acme.entities.individual.lectures.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepShowService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Peep object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePeepById(id);
		super.getBuffer().setData("course", object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;	
		tuple = super.unbind(object, "title", "nick", "instantiationMoment", "message", "email", "link");
		super.getResponse().setData(tuple);
	}

}
