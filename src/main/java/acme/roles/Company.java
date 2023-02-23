
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
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
public class Company extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Length(max = 76)
	protected String name;

	@NotNull
	@NotBlank
	@Length(max = 26)
	protected String vatNumber;

	@NotNull
	@NotBlank
	@Max(value = 101)
	protected String summary;

	@URL
	protected String link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
