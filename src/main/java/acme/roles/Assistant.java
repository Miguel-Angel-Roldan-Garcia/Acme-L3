
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;

@Entity
public class Assistant extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(min = 1, max = 101)
	protected String			supervisor;

	@NotBlank
	@Length(min = 1, max = 101)
	protected String			expertiseFields;

	@NotBlank
	@Length(min = 1, max = 101)
	protected String			resume;

	@URL
	protected String			link;

}
