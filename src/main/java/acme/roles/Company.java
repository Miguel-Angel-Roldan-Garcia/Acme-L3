
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Max(value = 76, message = "Must be shorter than 76 characters")
	protected String			name;

	@NotBlank
	@Max(value = 26, message = "Must be shorter than 26 characters")
	protected String			ivaNum;

	@NotBlank
	@Max(value = 101, message = "Must be shorter than 101 characters")
	protected String			resume;

	@URL(message = "Must be a valid URL")
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
