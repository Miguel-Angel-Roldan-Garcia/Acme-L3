
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lectures")
@Getter
@Setter
public class Lecture extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    private static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
    @Size(max = 76, message = "Must be shorter than 76 characters")
    private String title;

    @NotBlank
    @Size(max = 101, message = "Must be shorter than 101 characters")
    private String summary; // abstract can not be used
    @Min(1)
    private Integer estLearningTime;
    @NotBlank
    @Size(max = 76, message = "Must be shorter than 101 characters")
    private String body;

    @NotNull
    private Nature nature;

    @URL(message = "Must be a valid URL") //
    private String link;

}
