package acme.features.students.studentDashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.forms.individual.students.StudentDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

    @Autowired
    protected StudentDashboardRepository repository;

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
	int studentId;
	final StudentDashboard dashboard = new StudentDashboard();
	final Map<Nature, Integer> activityCount;
	final Statistic activitiesInWorkbookStatistics;
	final Statistic coursesOfStudentStatistics;

	studentId = super.getRequest().getPrincipal().getActiveRoleId();

	activityCount = this.repository.numberOfActivitiesByNature(studentId);

	activitiesInWorkbookStatistics = new Statistic();
	activitiesInWorkbookStatistics.setCount(this.repository.countOfActivitiesByStudentId(studentId));
	if (activitiesInWorkbookStatistics.getCount() > 0) {
	    activitiesInWorkbookStatistics
		    .setAverageValue(this.repository.averageTimeOfActivitiesByStudentId(studentId));
	    activitiesInWorkbookStatistics.setDeviationValue(this.repository.deviationTimeOfActivitiesByStudentId(
		    studentId, activitiesInWorkbookStatistics.getAverageValue(),
		    activitiesInWorkbookStatistics.getCount()));
	    activitiesInWorkbookStatistics
		    .setMinValue(this.repository.minPeriodTimeOfActivitiesOfEnrolmentsByStudentId(studentId));
	    activitiesInWorkbookStatistics
		    .setMaxValue(this.repository.maxPeriodTimeOfActivitiesOfEnrolmentsByStudentId(studentId));
	}

	coursesOfStudentStatistics = new Statistic();
	coursesOfStudentStatistics.setCount(this.repository.countOfDistinctCoursesOfStudent(studentId));

	if (coursesOfStudentStatistics.getCount() > 0) {
	    coursesOfStudentStatistics
		    .setAverageValue(this.repository.averageTotalLearningTimeOfCoursesByStudentId(studentId));
	    coursesOfStudentStatistics
		    .setDeviationValue(this.repository.desviationTotalLearningTimeOfCoursesByStudentId(studentId,
			    coursesOfStudentStatistics.getAverageValue()));
	    coursesOfStudentStatistics
		    .setMinValue(this.repository.minimumTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId));
	    coursesOfStudentStatistics
		    .setMaxValue(this.repository.maximumTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId));
	}

	dashboard.setActivityCount(activityCount);
	dashboard.setActivitiesInEnrolments(activitiesInWorkbookStatistics);
	dashboard.setCoursesOfStudent(coursesOfStudentStatistics);

	super.getBuffer().setData(dashboard);
    }

    @Override
    public void unbind(final StudentDashboard object) {
	Tuple tuple;

	tuple = super.unbind(object, "activityCount", "activitiesInEnrolments", "coursesOfStudent");

	super.getResponse().setData(tuple);
    }

}
