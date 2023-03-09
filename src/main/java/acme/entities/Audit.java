
package acme.entities;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;

public class Audit extends AbstractEntity {

	// Serialisation identifier -----------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	// Derived attributes --------------------------------------

	//TODO implement complex derived attribute mark mode

	// Relationships -------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	protected AuditingRecord	auditingRecord;
}
