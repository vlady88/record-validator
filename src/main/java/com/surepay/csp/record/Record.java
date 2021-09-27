package com.surepay.csp.record;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * class representing a record; makes use of BigDecimal for best accuracy
 */
@Getter
public class Record {

    private final String reference;
    private final String iban;
    private final String description;
    private final BigDecimal startBalance;
    private final BigDecimal mutation;
    private final BigDecimal endBalance;
    private Status status;

    public enum Status {
        VALID,
        DUPLICATE,
        INVALID_BALANCE
    }

    public Record(String reference, String iban, String description, String startBalance, String mutation, String endBalance) {
        this.reference = reference;
        this.iban = iban;
        this.description = description;
        this.startBalance = new BigDecimal(startBalance);
        this.mutation = new BigDecimal(mutation.startsWith("+") ? mutation.substring(1) : mutation);
        this.endBalance = new BigDecimal(endBalance);
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
