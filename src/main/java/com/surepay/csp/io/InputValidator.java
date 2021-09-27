package com.surepay.csp.io;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.IBANValidator;

public class InputValidator {
    public boolean validateInput(String reference, String iban, String startBalance, String mutation, String endBalance) {
        // check that reference is numeric
        if(!reference.matches("\\d+")) {
            return false;
        }
        // check that IBAN is valid
        if(!IBANValidator.getInstance().isValid(iban)) {
            return false;
        }
        // check that start balance is a valid double
        if(!DoubleValidator.getInstance().isValid(startBalance)) {
            return false;
        }
        // check that mutation has the right format
        if(mutation.startsWith("+")) {
            if(!DoubleValidator.getInstance().isValid(mutation.substring(1))) {
                return false;
            }
        } else {
            if(!DoubleValidator.getInstance().isValid(mutation)) {
                return false;
            }
        }
        // check that end balance is a valid double
        if(!DoubleValidator.getInstance().isValid(endBalance)) {
            return false;
        }

        return true;
    }
}
