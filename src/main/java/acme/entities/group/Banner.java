
package acme.entities.group;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationOrLastUpdateDate;

	// TODO Custom restriction after instantiation or last update date
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				displayPeriodStartDate;

	// TODO Custom restriction must last at least for one week
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				displayPeriodEndDate;

	@NotBlank
	@URL
	protected String			pictureLink;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@NotBlank
	@URL
	protected String			targetWebDocumentLink;

}
