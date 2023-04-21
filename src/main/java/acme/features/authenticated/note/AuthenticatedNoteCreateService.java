package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

    @Autowired
    protected AuthenticatedNoteRepository repository;

    @Override
    public void check() {
	super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
	super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
	final Note object;
	object = new Note();
	object.setInstantiationMoment(MomentHelper.getCurrentMoment());
	object.setTitle("");
	object.setMessage("");
	super.getBuffer().setData(object);
    }

    @Override
    public void bind(final Note object) {
	assert object != null;

	super.bind(object, "instantiationMoment", "title", "message", "email", "link");
    }

    @Override
    public void validate(final Note object) {
	assert object != null;
	final Principal principal = super.getRequest().getPrincipal();
	final boolean confirmed = super.getRequest().getData("confirmed", boolean.class);
	final String name = super.getRequest().getData("name", String.class);
	if (!super.getBuffer().getErrors().hasErrors("confirmed"))
	    super.state(confirmed, "confirmed", "authenticated.note.form.error.not-confirmed-note");
	if (!super.getBuffer().getErrors().hasErrors("name"))
	    super.state(name != null && !name.isEmpty() && (name + principal.getUsername()).length() < 73, "confirmed",
		    "authenticated.note.form.error.not-correct-size");
    }

    @Override
    public void perform(final Note object) {
	assert object != null;
	final String name = super.getRequest().getData("name", String.class);
	final Principal principal = super.getRequest().getPrincipal();
	object.setAuthor("<" + principal.getUsername() + "," + name + ">");
	this.repository.save(object);
    }

    @Override
    public void unbind(final Note object) {
	assert object != null;

	Tuple tuple;
	final Principal principal = super.getRequest().getPrincipal();
	final boolean confirmed = super.getRequest().getData("confirmed", boolean.class);
	final String name = super.getRequest().getData("name", String.class);
	tuple = super.unbind(object, "instantiationMoment", "title", "message", "email", "link");
	if (confirmed)
	    tuple.put("confirmed", confirmed);
	if (name != null && !name.isEmpty() && (name + principal.getUsername()).length() < 73)
	    tuple.put("name", name);
	super.getResponse().setData(tuple);
    }

}
