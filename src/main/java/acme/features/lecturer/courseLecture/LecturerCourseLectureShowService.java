
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureShowService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository repository;

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
		int courseLectureId;
		CourseLecture courseLecture;

		courseLectureId = super.getRequest().getData("id", int.class);
		courseLecture = this.repository.findOneCourseLectureById(courseLectureId);
		status = courseLecture != null ?
			super.getRequest().getPrincipal().getActiveRoleId() == courseLecture.getCourse().getLecturer().getId() 
			: 
			false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple = new Tuple();	
		int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		tuple = super.unbind(object, "id");
		Collection<Course> courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		Collection<Lecture> lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		tuple.put("editable", object.getCourse().isDraftMode());
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "code", object.getLecture()));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("editable", object.getCourse().isDraftMode());
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
		super.getResponse().setGlobal("lectureId", object.getLecture().getId());
	}

}
