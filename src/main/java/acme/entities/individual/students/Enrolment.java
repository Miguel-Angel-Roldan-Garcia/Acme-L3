
package acme.entities.individual.students;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.individual.lectures.Course;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
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

    protected String lowerNibble;

    protected String holderName;

    String cvc;

    String creditCardNumber;

    Date expiryDate;

    public boolean isValidCvc() {
	return this.cvc != null && this.cvc.matches("^[0-9]{3}$");
    }

    public boolean isValidHolderName() {
	return this.holderName != null && this.holderName.length() > 0;
    }

    public boolean isValidCreditCardNumber() {
	return this.creditCardNumber != null && this.creditCardNumber.matches("^[0-9]{16}$");
    }

    public boolean isValidExpityDate() {
	return this.expiryDate != null && MomentHelper.isFuture(this.expiryDate);
    }

    public String getLowerNibbleFromValidCreditCardNumber() {
	return this.creditCardNumber.substring(this.creditCardNumber.length() - 5);
    }

}
