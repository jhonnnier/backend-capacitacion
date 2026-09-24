package com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.entrada;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentProcessingException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;

public interface ProcessPaymentService {
    Payment processPayment(ProcessPaymentCommand command) throws PaymentValidationException, PaymentProcessingException;
}
