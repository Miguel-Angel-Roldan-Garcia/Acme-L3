
package acme.entities.group;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	//author must be=〈username〉 - 〈surname, name〉
	@NotBlank
	@Length(max = 75)
	protected String			author;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

}
