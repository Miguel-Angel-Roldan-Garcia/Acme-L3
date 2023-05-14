
package acme.entities.individual.students;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import acme.entities.individual.lectures.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

    protected static final long serialVersionUID = 1L;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
    protected String code;

    @NotBlank
    @Length(max = 75)
    protected String motivation;

    @NotBlank
    @Length(max = 100)
    protected String goals;

    @ManyToOne(optional = false)
    @Valid
    @NotNull
    protected Student student;

    protected boolean draftMode;

    @ManyToOne(optional = false)
    @Valid
    @NotNull
    protected Course course;

    // TODO Derived attribute=> Double workingTime=
    // sum(timePeriod of all of their activities)

    // OPTIONAL

    @CreditCardNumber
    @Transient
    public String creditCardNumber;

    @Transient
    @Pattern(regexp = "^[0-9]{3}$")
    public String cvc;

    @Transient
    @Pattern(regexp = "^\\d{2}/\\d{2}$")
    public String expiryDate;

    protected String lowerNibble;

    @Length(max = 24)
    protected String holderName;

}
