package acme.datatypes;

import java.util.Date;

import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardData {
    String cvc;

    String creditCardNumber;

    String holderName;

    Date expiryDate;

    public boolean isValidCvc() {
	return this.cvc != null && this.cvc.matches("^[0-9]{3}$");
    }

    public boolean isValidHolderName() {
	return this.holderName != null && this.holderName.length() > 0;
    }

    public boolean isValidCreditCard() {
	return this.creditCardNumber != null && this.creditCardNumber.matches("^[0-9]{16}$");
    }

    public boolean isValidExpityDate() {
	return this.expiryDate != null && MomentHelper.isFuture(this.expiryDate);
    }

    public String getLowerNibbleFromValidCreditCardNumber() {
	return this.creditCardNumber.substring(this.creditCardNumber.length() - 5);
    }

}
