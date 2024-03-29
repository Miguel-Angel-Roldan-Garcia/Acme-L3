
package acme.entities.individual.students;

import java.util.Date;

import javax.persistence.Entity;
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
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Activity extends AbstractEntity {

    protected static final long serialVersionUID = 1L;

    @NotBlank
    @Length(max = 75)
    protected String title;

    @NotBlank
    @Length(max = 100)
    protected String abstract$;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    protected Date initialDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    protected Date finishDate;
    // TODO Custom restriction=> initialDate must be < finishDate
    // & finishDate must be > iniialDate

    @URL
    protected String link;

    @NotNull
    protected Nature nature;

    @ManyToOne(optional = false)
    @Valid
    @NotNull
    protected Enrolment enrolment;

    // TODO Derived attribute=> Double timePeriod= finishDate-initialDate
    public Double getTimePeriod() {
	return MomentHelper.computeDuration(this.initialDate, this.finishDate).getSeconds() / 3600.;
    }

}
