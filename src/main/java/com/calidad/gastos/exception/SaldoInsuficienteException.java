package com.calidad.gastos.exception;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(BigDecimal saldoActual, BigDecimal montoSolicitado) {
        super(String.format("Saldo insuficiente: disponible %s, solicitado %s",
                saldoActual, montoSolicitado));
    }
}