
package acme.entities.group;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				instantiationMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayPeriodStartDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayPeriodEndDate;

	@NotBlank
	@URL
	protected String			pictureLink;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@NotBlank
	@URL
	protected String			targetWebDocumentLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public boolean isBeingDisplayed() {
		return MomentHelper.isAfterOrEqual(MomentHelper.getCurrentMoment(), this.displayPeriodStartDate) //
			&& MomentHelper.isBeforeOrEqual(MomentHelper.getCurrentMoment(), this.displayPeriodEndDate);
	}

}
