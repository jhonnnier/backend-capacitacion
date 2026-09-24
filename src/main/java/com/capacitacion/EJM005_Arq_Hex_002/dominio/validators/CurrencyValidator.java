package com.capacitacion.EJM005_Arq_Hex_002.dominio.validators;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import org.springframework.stereotype.Component;

@Component
public class CurrencyValidator extends PaymentValidator {
    @Override
    public void validate(ProcessPaymentCommand command) throws PaymentValidationException {
        if (command.currency() == null || command.currency().trim().isEmpty()) {
            throw new PaymentValidationException("La moneda no puede estar vacía.");
        }
        validateNext(command);
    }
}
