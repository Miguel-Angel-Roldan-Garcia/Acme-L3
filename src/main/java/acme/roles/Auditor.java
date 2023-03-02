package acme.roles;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole{
	

	protected static final long	serialVersionUID = 1L;

	@NotNull
	@Length(max =75)
	protected String firm;
	
	@NotNull
	@Length(max =25)
	protected String professionalId;
	
	@NotNull
	@Length(max =100)
	protected List<String> certifications;
	
	@URL
	protected String infoLink;
	
}
