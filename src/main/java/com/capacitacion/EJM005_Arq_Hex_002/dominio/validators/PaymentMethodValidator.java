package com.capacitacion.EJM005_Arq_Hex_002.dominio.validators;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodValidator extends PaymentValidator {
    @Override
    public void validate(ProcessPaymentCommand command) throws PaymentValidationException {
        if (command.paymentMethod() == null || command.paymentMethod().trim().isEmpty()) {
            throw new PaymentValidationException("El método de pago no puede estar vacío.");
        }
        validateNext(command);
    }
}
