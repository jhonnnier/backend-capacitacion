package com.capacitacion.EJM005_Arq_Hex_002.dominio.validators;


import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;

public abstract class PaymentValidator {
    private PaymentValidator nextValidator;

    public void setNext(PaymentValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public abstract void validate(ProcessPaymentCommand command) throws PaymentValidationException;

    protected void validateNext(ProcessPaymentCommand command) throws PaymentValidationException {
        if (nextValidator != null) {
            nextValidator.validate(command);
        }
    }
}
