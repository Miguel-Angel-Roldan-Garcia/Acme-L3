
package acme.entities.individual.auditors;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Mark;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				finishDate;

	@URL
	protected String			link;

	@NotNull
	protected Mark				mark;

	@NotNull
	protected boolean			draftMode;

	@NotNull
	protected boolean			correction;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getDurationInHours() {
		Double duration;

		duration = MomentHelper.computeDuration(this.startDate, this.finishDate).getSeconds() / 3600.;

		return duration;
	}

	// Relationships ----------------------------------------------------------


	@Valid
	@ManyToOne(optional = false)
	protected Audit audit;
}
