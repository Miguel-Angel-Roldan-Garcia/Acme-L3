
package acme.entities.ofStudents;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			summary;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishDate;
	//habría que poner una restricción custom sobre la fecha inicial y final

	@URL
	protected String			moreInfo;

	protected ActivityType		type;

}
