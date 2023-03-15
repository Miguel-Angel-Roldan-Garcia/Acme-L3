
package acme.entities.individual.auditors;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

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
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				auditingDate;

	@URL
	protected String			link;

	//	@NotNull
	//	protected String			mark;

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;
}
