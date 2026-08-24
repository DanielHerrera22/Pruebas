package com.calidad.gastos.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Presupuesto {

    private final Categoria categoria;
    private final BigDecimal limite;

    public Presupuesto(Categoria categoria, BigDecimal limite) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría es obligatoria");
        this.limite = validarLimite(limite);
    }

    private static BigDecimal validarLimite(BigDecimal limite) {
        if (limite == null || limite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El límite del presupuesto debe ser mayor que cero");
        }
        return limite;
    }

    public Categoria getCategoria() { return categoria; }
    public BigDecimal getLimite() { return limite; }

    public boolean excede(BigDecimal montoGastado) {
        Objects.requireNonNull(montoGastado, "El monto gastado no puede ser nulo");
        return montoGastado.compareTo(limite) > 0;
    }
}