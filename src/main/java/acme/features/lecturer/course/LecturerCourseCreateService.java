
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Course;
import acme.entities.individual.lectures.Lecture;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		Course object;
		Lecturer lecturer;
		String currentCurrency;
		Money money;
		
		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		
		currentCurrency = this.repository.findCurrentSystemCurrency();
		money = new Money();
		money.setAmount(1.);
		money.setCurrency(currentCurrency);
		
		object = new Course();
		object.setCode("");
		object.setTitle("");
		object.setAbstract$("");
		object.setRetailPrice(money);
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "abstract$", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) { //custom restrictions
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
		//must be in draft mode at creation
		super.state(object.isDraftMode(), "*", "lecturer.course.form.error.not-draft-mode");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

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
