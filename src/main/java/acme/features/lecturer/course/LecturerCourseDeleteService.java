
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.CourseLecture;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		int courseId;
		Course course;
		Lecturer lecturer;
		
		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		
		if(course != null) {
			lecturer = course.getLecturer();
			
			status = course.isDraftMode()
				&& super.getRequest().getPrincipal().getActiveRoleId() == lecturer.getId();
		}else {
			status = false;
		}
		
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;
		
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		
		super.bind(object, "code", "title", "abstract$", "retailPrice","link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;	
		//must be in draft mode to delete
		super.state(object.isDraftMode(), "*", "lecturer.course.form.error.not-draft-mode");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		Collection<CourseLecture> courseLectures;

		courseLectures = this.repository.findManyCourseLecturesByCourseId(object.getId());
		this.repository.deleteAll(courseLectures);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Collection<Lecture> lectures;
		lectures = this.repository.findManyLecturesByCourseId(object.getId());

		int theoreticalCount = 0;
		int handsOnCount = 0;
		for (final Lecture lecture : lectures) {
			if(lecture.getNature().equals(Nature.HANDS_ON)) {
				handsOnCount ++;
			}else {
				theoreticalCount ++;
			}
		}
		Nature nature = theoreticalCount == handsOnCount? Nature.BALANCED : theoreticalCount > handsOnCount ? Nature.THEORETICAL : Nature.HANDS_ON;
		
		boolean publishable = object.isDraftMode() && lectures != null && !lectures.isEmpty();
		tuple = super.unbind(object, "code", "title", "abstract$", "retailPrice","draftMode","link");
		
		tuple.put("nature", nature);
		tuple.put("publishable", publishable);

		super.getResponse().setData(tuple);
	}

}
