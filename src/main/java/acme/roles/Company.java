
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
	@Max(value = 76)
	private String				name;

	@NotBlank
	@Max(value = 26)
	private String				ivaNum;

	@NotBlank
	@Max(value = 101)
	private String				resume;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
