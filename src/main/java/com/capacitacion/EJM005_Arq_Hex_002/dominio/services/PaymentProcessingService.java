package com.capacitacion.EJM005_Arq_Hex_002.dominio.services;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentProcessingException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.entrada.ProcessPaymentService;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentGateway;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.salida.PaymentRepository;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.validators.PaymentValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentProcessingService implements ProcessPaymentService {

    PaymentGateway paymentGateway = null;
    PaymentRepository paymentRepository = null;
    List<PaymentValidator> validators = null;

    public PaymentProcessingService() {
    }

    public PaymentProcessingService(PaymentGateway paymentGateway, PaymentRepository paymentRepository, List<PaymentValidator> validators) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.validators = validators;
    }

    @Transactional
    @Override
    public Payment processPayment(ProcessPaymentCommand command) throws PaymentValidationException, PaymentProcessingException {
        // Configurar la Cadena de Responsabilidades
        if (!validators.isEmpty()) {
            PaymentValidator firstValidator = validators.get(0);
            for (int i = 0; i < validators.size() - 1; i++) {
                validators.get(i).setNext(validators.get(i + 1));
            }
            firstValidator.validate(command);
        }

        Payment payment = new Payment(command.amount(), command.currency(), command.paymentMethod());
        Payment processedPayment = paymentGateway.executePayment(payment);
        processedPayment.setStatus("COMPLETED");
        return paymentRepository.save(processedPayment);
    }
}
