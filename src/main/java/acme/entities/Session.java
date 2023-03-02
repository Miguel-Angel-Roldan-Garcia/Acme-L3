
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Length(max = 76)
	protected String title;

	@NotNull
	@NotBlank
	@Length(max = 101)
	protected String summary;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date startDate;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date finishDate;

	@URL
	protected String link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
