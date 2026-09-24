package com.capacitacion.EJM005_Arq_Hex_002.dominio;

import java.math.BigDecimal;
import java.util.Map;

public record ProcessPaymentCommand(
        BigDecimal amount,
        String currency,
        String paymentMethod,
        Map<String, String> paymentDetails
) {
}
