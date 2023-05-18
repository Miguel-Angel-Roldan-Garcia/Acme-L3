
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
public class LecturerLecturePublishService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureId;
		Lecture lecture;
		Lecturer lecturer;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = lecture != null && lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

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
		if(!super.getBuffer().getErrors().hasErrors()) {
			super.state(object.isDraftMode(), "*", "lecturer.lecture.form.error.not-draft-mode");
			Lecture stored = this.repository.findOneLectureById(object.getId());
			super.state(stored.equals(object),"*","lecturer.lecture.form.cant-update-and-publish-simultaneous"); 
		}
		//Unique code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Lecture existing;
			existing = this.repository.findOneLectureByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.lecture.form.error.duplicated");
		}
		if(!super.getBuffer().getErrors().hasErrors("nature")) {
			super.state(object.getNature() != Nature.BALANCED, "nature", "lecturer.lecture.form.error.nature-balanced");
		}
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;	
		tuple = super.unbind(object,"code", "title", "abstract$", "estimatedLearningTime", "body", "nature","draftMode", "link");
		tuple.put("natures", SelectChoices.from(Nature.class, object.getNature()));
		super.getResponse().setData(tuple);
	}

}
