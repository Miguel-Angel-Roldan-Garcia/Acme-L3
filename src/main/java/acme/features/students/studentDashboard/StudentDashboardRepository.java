package acme.features.students.studentDashboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.entities.individual.lectures.Course;
import acme.entities.individual.students.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

    // -------ACTIVITIES STATISTICS--------------------

    @Query("select avg(TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.) from Activity act where act.enrolment.student.id = :studentId")
    Double averageTimeOfActivitiesByStudentId(int studentId);

    @Query("select max(TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.) from Activity act where act.enrolment.student.id =:studentId")
    Double maxPeriodTimeOfActivitiesOfEnrolmentsByStudentId(int studentId);

    @Query("select min(TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.) from Activity act where act.enrolment.student.id =:studentId")
    Double minPeriodTimeOfActivitiesOfEnrolmentsByStudentId(int studentId);

    @Query("select act.nature from Activity act where act.enrolment.student.id =:studentId")
    List<Nature> findAllNaturesUsedInActivitiesOfStudent(int studentId);

    default Map<Nature, Integer> numberOfActivitiesByNature(final int studentId) {
	final List<Nature> usedNatures = this.findAllNaturesUsedInActivitiesOfStudent(studentId);
	final Map<Nature, Integer> activityCountPerNature = new HashMap<>();
	for (final Nature nature : usedNatures)
	    if (activityCountPerNature.containsKey(nature))
		activityCountPerNature.put(nature, activityCountPerNature.get(nature) + 1);
	    else
		activityCountPerNature.put(nature, 1);
	return activityCountPerNature;
    }

    @Query("select count(act) from Activity act where act.enrolment.student.id =:studentId")
    int countOfActivitiesByStudentId(int studentId);

    @Query("select sqrt(sum(((TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.) - :averageValue)"
	    + "*((TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.) - :averageValue))) from Activity"
	    + " act where act.enrolment.student.id =:studentId")
    Double deviationTimeOfActivitiesByStudentIdSubQuery(int studentId, Double averageValue);

    default Double deviationTimeOfActivitiesByStudentId(final int studentId, final Double averageValue,
	    final int count) {
	return this.deviationTimeOfActivitiesByStudentIdSubQuery(studentId, averageValue) / Math.sqrt(count);
    }

    // -----ENROLMENT STATISTICS-----------------

    @Query("select count(enrolment) from Enrolment enrolment where enrolment.student.id = :studentId")
    int countOfEnrolmentsByStudentId(int studentId);

    @Query("select enrolment from Enrolment enrolment where enrolment.student.id =:studentId")
    List<Enrolment> findAllEnrolmentsByStudentId(int studentId);

    @Query("select sum((TIME_TO_SEC(TIMEDIFF(act.finishDate, act.initialDate)) / 3600.)) from Activity act where act.enrolment.id =:enrolmentId")
    Double totalTimeByEnrolmentId(int enrolmentId);

    default Map<Course, Double> totalTimeOfEnrolmentsPerStudentCourses(final int studentId) {
	final List<Enrolment> enrolmentsOfStudent = this.findAllEnrolmentsByStudentId(studentId);
	final Map<Course, Double> totalTimePerCourse = new HashMap<>();
	for (final Enrolment enrolment : enrolmentsOfStudent) {
	    final Course key = enrolment.getCourse();
	    final Double totalTime = this.totalTimeByEnrolmentId(enrolment.getId()) != null
		    ? this.totalTimeByEnrolmentId(enrolment.getId())
		    : 0.;
	    if (totalTimePerCourse.containsKey(key))
		totalTimePerCourse.put(key, totalTimePerCourse.get(key) + totalTime);
	    else
		totalTimePerCourse.put(key, totalTime);
	}
	return totalTimePerCourse;
    }

    default Double averageTotalTimeOfEnrolmentsInCoursesOfStudentId(final int studentId) {
	final Map<Course, Double> totalTimePerCourse = this.totalTimeOfEnrolmentsPerStudentCourses(studentId);
	return totalTimePerCourse.entrySet().stream().collect(Collectors.summingDouble(x -> x.getValue()))
		/ totalTimePerCourse.keySet().size();
    }

    default Double desviationTotalTimeOfEnrolmentsInCoursesOfStudentId(final int studentId, final Double averageValue) {
	final Map<Course, Double> totalTimePerCourse = this.totalTimeOfEnrolmentsPerStudentCourses(studentId);
	return Math.sqrt(totalTimePerCourse.entrySet().stream()
		.collect(Collectors.summingDouble(x -> Math.pow(x.getValue() - averageValue, 2.)))
		/ totalTimePerCourse.keySet().size());
    }

    default Double minimumTotalTimeOfEnrolmentsInCoursesOfStudentId(final int studentId) {
	final Map<Course, Double> totalTimePerCourse = this.totalTimeOfEnrolmentsPerStudentCourses(studentId);
	return totalTimePerCourse.values().stream().min(Comparator.naturalOrder()).get();
    }

    default Double maximumTotalTimeOfEnrolmentsInCoursesOfStudentId(final int studentId) {
	final Map<Course, Double> totalTimePerCourse = this.totalTimeOfEnrolmentsPerStudentCourses(studentId);
	return totalTimePerCourse.values().stream().max(Comparator.naturalOrder()).get();
    }

}
