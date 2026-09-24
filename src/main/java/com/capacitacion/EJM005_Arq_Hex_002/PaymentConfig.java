package com.capacitacion.EJM005_Arq_Hex_002;

import com.capacitacion.EJM005_Arq_Hex_002.adaptadores.in.web.PaymentController;
import com.capacitacion.EJM005_Arq_Hex_002.adaptadores.out.payment.CreditCardPaymentGateway;
import com.capacitacion.EJM005_Arq_Hex_002.adaptadores.out.payment.PaypalPaymentGateway;
import com.capacitacion.EJM005_Arq_Hex_002.adaptadores.out.persistence.InMemoryPaymentRepository;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentGateway;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentRepository;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.services.PaymentProcessingService;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.validators.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PaymentConfig {

    @Bean
    PaymentRepository paymentRepository() {
        return new InMemoryPaymentRepository();
    }

    @Bean
    AmountValidator amountValidator() {
        return new AmountValidator();
    }

    @Bean
    CurrencyValidator currencyValidator() {
        return new CurrencyValidator();
    }

    @Bean
    PaymentMethodValidator paymentMethodValidator() {
        return new PaymentMethodValidator();
    }

    @Bean
    PaymentDetailsValidator paymentDetailsValidator() {
        return new PaymentDetailsValidator();
    }

    @Bean
    List<PaymentValidator> paymentValidators(
            AmountValidator amountValidator,
            CurrencyValidator currencyValidator,
            PaymentMethodValidator paymentMethodValidator,
            PaymentDetailsValidator paymentDetailsValidator
    ) {
        return List.of(amountValidator, currencyValidator, paymentMethodValidator, paymentDetailsValidator);
    }

    @Bean
    PaymentProcessingService paymentProcessingService(
            // Aquí podríamos seleccionar la pasarela basada en alguna lógica
            @Qualifier("creditCardGateway") PaymentGateway paymentGateway,
            PaymentRepository paymentRepository,
            List<PaymentValidator> validators
    ) {
        return new PaymentProcessingService(paymentGateway, paymentRepository, validators);
    }

    @Bean
    PaymentController paymentController(PaymentProcessingService paymentProcessingService) {
        return new PaymentController(paymentProcessingService);
    }

    @Bean
    CreditCardPaymentGateway creditCardPaymentGateway() {
        return new CreditCardPaymentGateway();
    }

    @Bean
    PaypalPaymentGateway paypalPaymentGateway() {
        return new PaypalPaymentGateway();
    }
}
