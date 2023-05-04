
package acme.entities.individual.companies;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDate;

	@URL
	protected String			link;

	protected boolean			isAddendum;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getDurationInHours() {
		Double duration;

		duration = MomentHelper.computeDuration(this.startDate, this.endDate).getSeconds() / 3600.;

		return duration;
	}

	@Transient
	public Boolean isDraftMode() {
		return this.practicum.isDraftMode();
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Practicum practicum;
}
