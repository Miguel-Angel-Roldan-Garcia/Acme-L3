

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

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
		Course course;
		Lecturer lecturer;

		course = this.repository.findOneCourseById(super.getRequest().getData("id", int.class));
		if(course != null) {
			lecturer = course.getLecturer();
			status = lecturer.getId() == super.getRequest().getPrincipal().getActiveRoleId();
				
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
		//Unique code
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;
			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.duplicated");
		}
		//Money positive
		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			boolean moneyAmountStatus;

			moneyAmountStatus = object.getRetailPrice().getAmount() > 0;

			super.state(moneyAmountStatus, "retailPrice", "administrator.offer.form.error.price.negative-or-zero");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			boolean moneyAmountStatus;

			moneyAmountStatus = object.getRetailPrice().getAmount() < 1000000;

			super.state(moneyAmountStatus, "retailPrice", "administrator.offer.form.error.price.too-big");
		}
		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			boolean moneyCurrencyStatus;
			Collection<String> currencySystemConfiguration;

			currencySystemConfiguration = this.repository.findAllCurrencySystemConfiguration();
			moneyCurrencyStatus = currencySystemConfiguration.contains(object.getRetailPrice().getCurrency());

			super.state(moneyCurrencyStatus, "retailPrice", "administrator.offer.form.error.price.non-existent-currency");
		}
		Collection<Lecture> lecturesInCourse = this.repository.findManyLecturesByCourseId(object.getId());
		//at least one hands on
		super.state(lecturesInCourse.stream().anyMatch(lec->lec.getNature() == Nature.HANDS_ON), "*", "lecturer.course.form.error.not-hands-on");
		//all lectures published
		super.state(lecturesInCourse.stream().allMatch(l->!l.isDraftMode()),"*","lecturer.course.form.error.not-all-lectures-published");
		//must be in draft mode at moment of publish
		super.state(object.isDraftMode(), "*", "lecturer.course.form.error.not-draft-mode");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
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
