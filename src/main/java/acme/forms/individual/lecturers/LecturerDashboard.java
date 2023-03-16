
package acme.forms.individual.lecturers;

import java.util.Map;

import acme.datatypes.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	//total number of theory lectures
	//total number of hands-on lectures;
	Map<Nature, Integer>		lecturesCount;

	//average, deviation, minimum, and maximum learning time of the lectures;
	//	Statistic lectures;
	//average, deviation, minimum, and maximum learning time of the courses.
	//	Statistic courses;
}
