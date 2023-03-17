
package acme.entities.individual.lectures;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLecture extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Relationships ----------------------------------------------------------
    @NotNull
    @Valid
    @ManyToOne(optional = false)
    protected Course course;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    protected Lecture lecture;

    // TODO check same Lecturer for all lectures and Course
}
