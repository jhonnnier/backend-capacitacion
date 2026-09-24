package com.capacitacion.EJM005_Arq_Hex_002.adaptadores.in.web;

import com.capacitacion.EJM005_Arq_Hex_002.dominio.Payment;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.ProcessPaymentCommand;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentProcessingException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.exceptions.PaymentValidationException;
import com.capacitacion.EJM005_Arq_Hex_002.dominio.puertos.entrada.ProcessPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final ProcessPaymentService processPaymentService;

    public PaymentController(ProcessPaymentService processPaymentService) {
        this.processPaymentService = processPaymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody ProcessPaymentRequest request) {
        try {
            ProcessPaymentCommand command = new ProcessPaymentCommand(
                    request.amount(),
                    request.currency(),
                    request.paymentMethod(),
                    request.paymentDetails()
            );
            Payment payment = processPaymentService.processPayment(command);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (PaymentValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (PaymentProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public record ProcessPaymentRequest(
            BigDecimal amount,
            String currency,
            String paymentMethod,
            Map<String, String> paymentDetails
    ) {}
}
