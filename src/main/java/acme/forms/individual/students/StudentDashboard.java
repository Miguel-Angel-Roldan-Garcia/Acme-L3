
package acme.forms.individual.students;

import java.util.Map;

import acme.entities.individual.students.Enrolment;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long					serialVersionUID	= 1L;

	protected Map<Enrolment, Integer>			totalNumberOfTheoricalActivitiesPerEnrolment;

	protected Map<Enrolment, Integer>			totalNumberOfHandsOnActivitiesPerEnrolment;

	protected Map<Enrolment, StudentStadistics>	stadisticsOfTimePeriodFromActivies;

	protected StudentStadistics					stadisticsOfWorkingTimeFromEnrolments;

}
