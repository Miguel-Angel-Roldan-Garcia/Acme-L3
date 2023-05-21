
package acme.features.lecturer.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.forms.individual.lecturers.LecturerDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


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

		LecturerDashboard dashboard;
		int lecturerId;
		Statistic learningTimeInCourses;
		Statistic learningTimeInLectures;
		Map<Nature, Integer> lecturesCount;

		lecturesCount = new HashMap<>();
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturesCount.put(Nature.THEORETICAL, this.repository.lecturesCountByNatureOfLecturer(Nature.THEORETICAL, lecturerId));
		lecturesCount.put(Nature.HANDS_ON, this.repository.lecturesCountByNatureOfLecturer(Nature.HANDS_ON, lecturerId));

		learningTimeInCourses = new Statistic();
		learningTimeInCourses.setAverageValue(this.repository.averageEltInCoursesOfLecturer(lecturerId));
		learningTimeInCourses.setDeviationValue(this.repository.deviationEltInCoursesOfLecturer(lecturerId));
		learningTimeInCourses.setMaxValue(this.repository.maxEltInCoursesOfLecturer(lecturerId));
		learningTimeInCourses.setMinValue(this.repository.minEltInCoursesOfLecturer(lecturerId));

		learningTimeInLectures = new Statistic();
		learningTimeInLectures.setAverageValue(this.repository.averageEltInLecturesOfLecturer(lecturerId));
		learningTimeInLectures.setDeviationValue(this.repository.deviationEltInLecturesOfLecturer(lecturerId));
		learningTimeInLectures.setMaxValue(this.repository.maxEltInLecturesOfLecturer(lecturerId));
		learningTimeInLectures.setMinValue(this.repository.minEltInLecturesOfLecturer(lecturerId));

		dashboard = new LecturerDashboard();
		dashboard.setLearningTimeInCourses(learningTimeInCourses);
		dashboard.setLearningTimeInLectures(learningTimeInLectures);
		dashboard.setLecturesCount(lecturesCount);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;
		
		tuple = super.unbind(object, //
			"learningTimeInCourses", "learningTimeInLectures", // 
			"lecturesCount");
		tuple.put("theoreticalLecturesCount", object.getLecturesCount().get(Nature.THEORETICAL));
		tuple.put("handsonLecturesCount", object.getLecturesCount().get(Nature.HANDS_ON));
		
		
		super.getResponse().setData(tuple);
	}

}
