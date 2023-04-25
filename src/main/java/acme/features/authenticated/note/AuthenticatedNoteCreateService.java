package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.group.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
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
	UserAccount userAccount;
	Principal principal;

	object = new Note();
	object.setTitle("");
	object.setMessage("");

	final Date date = MomentHelper.getCurrentMoment();
	date.setSeconds(date.getSeconds() - 1);
	object.setInstantiationMoment(date);
	principal = super.getRequest().getPrincipal();
	userAccount = this.repository.findOneUserAccountById(principal.getAccountId());
	final String author = "" + principal.getUsername() + " - " + userAccount.getIdentity().getFullName();
	object.setAuthor(author);
	object.setEmail(userAccount.getIdentity().getEmail());
	super.getBuffer().setData(object);
    }

    @Override
    public void bind(final Note object) {
	assert object != null;

	super.bind(object, "title", "message", "link");
    }

    @Override
    public void validate(final Note object) {
	assert object != null;

	boolean confirmed = false;
	confirmed = this.getRequest().getData("confirmed", boolean.class);
	if (!super.getBuffer().getErrors().hasErrors("confirmed"))
	    super.state(confirmed, "confirmed", "authenticated.note.form.error.not-confirmed-note");
    }

    @Override
    public void perform(final Note object) {
	assert object != null;
	this.repository.save(object);
    }

    @Override
    public void unbind(final Note object) {
	assert object != null;

	Tuple tuple;

	tuple = super.unbind(object, "title", "message", "link");
	// cuando quieres utilizar objetos que no pertenecen a una entidad
	// durante un create-service, debes distinguir si estas haciendo un post y
	// cuando un get
	// ya que al principio haces un get, y luego al añadir los datos, es cuando
	// haces el post
	if (super.getRequest().getMethod().equals(HttpMethod.POST))
	    tuple.put("confirmed", super.getRequest().getData("confirmed", boolean.class));
	else
	    tuple.put("confirmed", "false");

	super.getResponse().setData(tuple);
    }

}
