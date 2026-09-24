package com.capacitacion.EJM005_Arq_Hex_002.dominio;


import java.math.BigDecimal;

public class Payment {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String status;

    public Payment(BigDecimal amount, String currency, String paymentMethod) {
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
