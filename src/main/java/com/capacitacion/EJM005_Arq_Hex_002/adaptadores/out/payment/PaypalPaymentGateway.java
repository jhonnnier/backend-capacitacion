package com.capacitacion.EJM005_Arq_Hex_002.adaptadores.out.payment;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentProcessingException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentGateway;
import org.springframework.stereotype.Component;

@Component("paypalGateway")
public class PaypalPaymentGateway implements PaymentGateway {
    @Override
    public Payment executePayment(Payment payment) throws PaymentProcessingException {
        if (payment.getPaymentMethod().equalsIgnoreCase("PAYPAL")) {
            System.out.println("Simulando pago con PayPal: " + payment.getAmount() + " " + payment.getCurrency());
            payment.setStatus("PROCESSED");
            return payment;
        }
        throw new PaymentProcessingException("Método de pago no soportado por esta pasarela.");
    }
}
