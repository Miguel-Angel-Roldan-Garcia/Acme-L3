
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
public class Student extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Max(75) // he puesto 75 porque Max salo aceptara los valores que son mayores o iguales
	protected String			statement;

	@NotBlank
	@Max(100)
	protected String			strongFeatures;

	@NotBlank
	@Max(100)
	protected String			weakFeatures;

	@URL
	protected String			moreInfo;

}
