
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.apache.catalina.users.AbstractRole;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String name;

	@Column(unique = true)
	@NotBlank
	@Length(max = 25)
	//TODO
	//@Column(unique=true)
	protected String vatNumber;

	@NotBlank
	@Length(max = 100)
	protected String summary;

	@URL
	protected String link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
