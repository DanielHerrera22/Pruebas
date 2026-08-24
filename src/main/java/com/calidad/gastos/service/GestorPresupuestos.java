package com.calidad.gastos.service;

import com.calidad.gastos.exception.PresupuestoInvalidoException;
import com.calidad.gastos.model.Categoria;
import com.calidad.gastos.model.Presupuesto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GestorPresupuestos {

    private final Map<Categoria, Presupuesto> presupuestos = new HashMap<>();

    public void definirPresupuesto(Categoria categoria, BigDecimal limite) {
        if (categoria == null) {
            throw new PresupuestoInvalidoException("La categoría del presupuesto no puede ser nula");
        }
        try {
            presupuestos.put(categoria, new Presupuesto(categoria, limite));
        } catch (IllegalArgumentException causaOriginal) {
            throw new PresupuestoInvalidoException(causaOriginal.getMessage());
        }
    }

    public Optional<Presupuesto> obtenerPresupuesto(Categoria categoria) {
        return Optional.ofNullable(presupuestos.get(categoria));
    }

    public boolean estaExcedido(Categoria categoria, BigDecimal montoGastado) {
        return obtenerPresupuesto(categoria)
                .map(p -> p.excede(montoGastado))
                .orElse(false);
    }
}