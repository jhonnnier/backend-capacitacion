package com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentProcessingException;

public interface PaymentGateway {
    Payment executePayment(Payment payment) throws PaymentProcessingException;
}
