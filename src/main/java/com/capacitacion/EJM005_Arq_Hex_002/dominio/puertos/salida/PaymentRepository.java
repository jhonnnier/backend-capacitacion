package com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
