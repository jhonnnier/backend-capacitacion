package com.capacitacion.EJM005_Arq_Hex_002.dominio.validators;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountValidator extends PaymentValidator {
    @Override
    public void validate(ProcessPaymentCommand command) throws PaymentValidationException {
        if (command.amount() == null || command.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentValidationException("El monto debe ser mayor que cero.");
        }
        validateNext(command);
    }
}
