package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteShowService extends AbstractService<Authenticated, Note> {

    @Autowired
    protected AuthenticatedNoteRepository repository;

    @Override
    public void check() {
	boolean status;

	status = super.getRequest().hasData("id", int.class);

	super.getResponse().setChecked(status);
    }

    // SI HAY QUE CAMBIAR LA FORMA DEL LISTADO, EL AUTHORISE HAY QUE MODIFICARLO
    @Override
    public void authorise() {
	boolean status;
	int masterId;
	Note note;

	masterId = super.getRequest().getData("id", int.class);
	note = this.repository.findOneNoteById(masterId);
	status = note != null;

	super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
	Note object;
	int id;

	id = super.getRequest().getData("id", int.class);
	object = this.repository.findOneNoteById(id);
	super.getBuffer().setData(object);
    }

    @Override
    public void unbind(final Note object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "instantiationMoment", "title", "message", "email", "link", "author");
	super.getResponse().setData(tuple);

    }

}
