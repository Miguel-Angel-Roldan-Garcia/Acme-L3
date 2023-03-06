
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class TutorialSession extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			tittle;

	@NotBlank
	@Length(max = 100)
	protected String			_abstract;

	/* TODO Derived attribute from contained sessions */
	@NotNull
	protected Nature			sessionType;

	/* TODO Custom restriction time ahead */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDate;

	/* TODO Custom restriction one up to five hour long */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDate;

	@URL
	protected String			link;

}
