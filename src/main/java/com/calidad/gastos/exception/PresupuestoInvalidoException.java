package com.calidad.gastos.exception;

public class PresupuestoInvalidoException extends RuntimeException {
    public PresupuestoInvalidoException(String mensaje) {
        super(mensaje);
    }
}