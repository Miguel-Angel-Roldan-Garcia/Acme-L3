
package acme.entities.individual.auditors;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class AuditingRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	// TODO Custom restriction at least one hour long
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				auditingDate;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	//TODO implement complex derived attribute mark

	// Relationships ----------------------------------------------------------

}
