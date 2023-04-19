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
	final Statistic enrolmentsInCoursesStatistics;

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

	enrolmentsInCoursesStatistics = new Statistic();
	enrolmentsInCoursesStatistics.setCount(this.repository.countOfEnrolmentsByStudentId(studentId));

	if (enrolmentsInCoursesStatistics.getCount() > 0) {
	    enrolmentsInCoursesStatistics
		    .setAverageValue(this.repository.averageTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId));
	    enrolmentsInCoursesStatistics
		    .setDeviationValue(this.repository.desviationTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId,
			    enrolmentsInCoursesStatistics.getAverageValue()));
	    enrolmentsInCoursesStatistics
		    .setMinValue(this.repository.minimumTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId));
	    enrolmentsInCoursesStatistics
		    .setMaxValue(this.repository.maximumTotalTimeOfEnrolmentsInCoursesOfStudentId(studentId));
	}

	dashboard.setActivityCount(activityCount);
	dashboard.setActivitiesInEnrolments(activitiesInWorkbookStatistics);
	dashboard.setEnrolmentsInCourses(enrolmentsInCoursesStatistics);

	super.getBuffer().setData(dashboard);
    }

    @Override
    public void unbind(final StudentDashboard object) {
	Tuple tuple;

	tuple = super.unbind(object, "activityCount", "activitiesInEnrolments", "enrolmentsInCourses");

	super.getResponse().setData(tuple);
    }

}
