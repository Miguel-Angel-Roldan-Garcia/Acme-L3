
package acme.datatypes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.individual.assistants.Tutorial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorialNatureClassification {

	@Valid
	@NotNull
	protected Tutorial	tutorial;

	@NotNull
	protected Nature	nature;


	public TutorialNatureClassification(@Valid @NotNull final Tutorial tutorial, @NotNull final int nature) {
		this.tutorial = tutorial;
		switch (nature) {
		case 0:
			this.nature = Nature.THEORETICAL;
			break;
		case 1:
			this.nature = Nature.HANDS_ON;
			break;
		default:
			this.nature = Nature.BALANCED;
		}
	}

}
