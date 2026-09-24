package com.capacitacion.EJM005_Arq_Hex_002.adaptadores.out.persistence;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<Long, Payment> payments = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Payment save(Payment payment) {
        payment.setId(nextId.getAndIncrement());
        payments.put(payment.getId(), payment);
        System.out.println("Pago guardado con ID: " + payment.getId());
        return payment;
    }
}
