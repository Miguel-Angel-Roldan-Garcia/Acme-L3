
package acme.entities.group;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String head;

	@NotBlank
	@Length(max = 100)
	protected String summary;

	// TODO Custom restriction D02-G-010
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date availabilityPeriodStartDate;

	// TODO Custom restriction D02-G-010
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date availabilityPeriodEndDate;

	@Min(value = 0)
	protected Integer price;

	@URL
	protected String link;

}
