
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "students")
public class Student extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Max(value = 75, message = "Must be shorter than 76 characters") // he puesto 75 porque Max salo aceptara los valores que son mayores o iguales
	protected String			statement;

	@NotBlank
	@Max(value = 100, message = "Must be shorter than 101 characters")
	protected String			strongFeatures;

	@NotBlank
	@Max(value = 100, message = "Must be shorter than 101 characters")
	protected String			weakFeatures;

	@URL
	protected String			moreInfo;

}
