
package acme.entities.group;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String heading;

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

	@NotNull
	protected Money price;

	@URL
	protected String link;

}
