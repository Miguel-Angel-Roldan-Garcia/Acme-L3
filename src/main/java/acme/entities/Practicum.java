
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String code;

	@NotBlank
	@Length(max = 75)
	protected String title;

	@NotBlank
	@Length(max = 100)
	protected String _abstract;

	@NotBlank
	@Length(max = 100)
	protected String goals;

	// Derived attributes -----------------------------------------------------

	/* TODO Derived attribute estimatedTotalTime D02-S4-5 */

	// Relationships ----------------------------------------------------------

}
