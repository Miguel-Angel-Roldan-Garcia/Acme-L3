
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
	@Length(max = 75)
	protected String			supervisor;

	@Length(max = 100)
	protected String			expertiseFields;

	@Length(max = 100)
	protected String			resume;

	@URL
	protected String			link;

}
