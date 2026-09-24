package com.capacitacion.EJM005_Arq_Hex_002.dominio.validators;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsValidator extends PaymentValidator {
    @Override
    public void validate(ProcessPaymentCommand command) throws PaymentValidationException {
        if (command.paymentDetails() == null || command.paymentDetails().isEmpty()) {
            throw new PaymentValidationException("Los detalles de pago son requeridos.");
        }
        validateNext(command);
    }
}
