package com.calidad.gastos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Transaccion {

    private final TipoTransaccion tipo;
    private final BigDecimal monto;
    private final Categoria categoria;
    private final String descripcion;
    private final LocalDate fecha;

    public Transaccion(TipoTransaccion tipo, BigDecimal monto, Categoria categoria,
                        String descripcion, LocalDate fecha) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo de transacción es obligatorio");
        this.categoria = Objects.requireNonNull(categoria, "La categoría es obligatoria");
        this.fecha = Objects.requireNonNull(fecha, "La fecha es obligatoria");
        this.monto = validarMonto(monto);
        this.descripcion = (descripcion == null) ? "" : descripcion.trim();
    }

    private static BigDecimal validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        return monto;
    }

    public TipoTransaccion getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public Categoria getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s en %s (%s)", fecha, tipo, monto, categoria, descripcion);
    }
}