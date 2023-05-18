
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		Boolean status;
		
		status = super.getRequest().getPrincipal().hasRole("acme.roles.Lecturer");
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());


		object = new Lecture();
		object.setTitle("");
		object.setAbstract$("");
		object.setBody("");
		object.setEstimatedLearningTime(1.);
		object.setNature(Nature.THEORETICAL);
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object,"code", "title", "abstract$", "body", "estimatedLearningTime", "nature", "link");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		//Unique code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Lecture existing;
			existing = this.repository.findOneLectureByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.lecture.form.error.duplicated");
		}
		if(!super.getBuffer().getErrors().hasErrors("nature")) {
			super.state(object.getNature() != Nature.BALANCED, "nature", "lecturer.lecture.form.error.nature-balanced");
		}
		super.state(object.isDraftMode(), "*", "lecturer.lecture.form.error.not-draft-mode");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;	
		tuple = super.unbind(object, "code","title", "abstract$", "estimatedLearningTime", "body", "nature","draftMode", "link");
		tuple.put("natures", SelectChoices.from(Nature.class, object.getNature()));
		super.getResponse().setData(tuple);
	}

}
