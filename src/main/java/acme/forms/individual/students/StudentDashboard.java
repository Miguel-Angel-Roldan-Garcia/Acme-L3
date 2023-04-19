
package acme.forms.individual.students;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected Map<Nature, Integer>	activityCount;

	protected Statistic				enrolmentsInCourses;

	protected Statistic				activitiesInEnrolments;

}
