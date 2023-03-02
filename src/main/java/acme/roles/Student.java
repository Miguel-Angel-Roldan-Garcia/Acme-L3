
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
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
	@Length(max = 76)
	protected String			statement;

	@NotBlank
	@Length(max = 101)
	protected String			strongFeatures;

	@NotBlank
	@Length(max = 101)
	protected String			weakFeatures;

	@URL
	protected String			link;

}
