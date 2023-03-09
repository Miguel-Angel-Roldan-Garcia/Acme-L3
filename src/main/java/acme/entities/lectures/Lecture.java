
package acme.entities.lectures;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
    @Length(max = 75)
    protected String title;

    @NotBlank
    @Length(max = 100)
    protected String abstract1; // abstract can not be used

    @NotNull
    @Min(1)
    protected Integer estimatedLearningTime;

    @NotBlank
    @Length(max = 100)
    protected String body;

    @NotNull
    protected Nature nature;

    @URL
    protected String link;

}
