
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String title;

	@NotBlank
	@Length(max = 100)
	protected String _abstract;

	/* TODO Custom restriction label D02-S4-6 */
	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date startDate;

	/* TODO Custom restriction label D02-S4-6 */
	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date finishDate;

	@URL
	protected String link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	protected Practicum practicum;
}
