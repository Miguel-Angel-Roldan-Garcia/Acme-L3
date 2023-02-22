
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
    @Size(max = 75, message = "Must be shorter than 76 characters")
    protected String almaMater;

    @NotBlank
    @Size(max = 100, message = "Must be shorter than 101 characters")
    protected String resume;

    @NotBlank
    @Size(max = 100, message = "Must be shorter than 101 characters")
    protected String qualifications;
    @URL(message = "Must be a valid URL")
    protected String link;

    // Derived attributes -----------------------------------------------------

    // Relationships ----------------------------------------------------------
    // TODO
}
