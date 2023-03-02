
package acme.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String code;

	@NotNull
	@NotBlank
	@Length(max = 76)
	protected String title;

	@NotNull
	@NotBlank
	@Length(max = 101)
	protected String summary;

	@NotNull
	@NotBlank
	@Length(max = 101)
	protected String goals;

	// Derived attributes -----------------------------------------------------

	@NotNull
	protected Double estimatedTotalTime;

	// Relationships ----------------------------------------------------------

	@ManyToOne
	protected List<Session> sessions;

}
