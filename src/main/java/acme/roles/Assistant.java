
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;

@Entity
public class Assistant extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 101)
	protected String			supervisor;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 101)
	protected String			expertiseFields;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 101)
	protected String			resume;

	@NotBlank
	@URL
	protected String			link;

}
