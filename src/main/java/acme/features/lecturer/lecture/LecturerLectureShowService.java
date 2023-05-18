
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
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

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

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		status = lecture != null && super.getRequest().getPrincipal().getActiveRoleId() == lecture.getLecturer().getId();

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
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;	
		tuple = super.unbind(object, "code","title", "abstract$", "estimatedLearningTime", "body", "nature","draftMode", "link");
		tuple.put("natures", SelectChoices.from(Nature.class, object.getNature()));
		super.getResponse().setData(tuple);
	}

}
