
package acme.entities.individual.students;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;

public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			_abstract;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishDate;
	//habría que poner una restricción custom sobre la fecha inicial y final
	//faltará la propiedad derivada que calcule el periodo

	@URL
	protected String			link;

	@NotNull
	protected Nature			_type;

	@ManyToOne(optional = false)
	@Valid
	protected Enrolment			enrolment;

}
