
package acme.entities.enrolments;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Enrolment extends AbstractEntity {

	protected static final long serialVersionUID = 1L;

}
