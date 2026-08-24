package com.calidad.gastos.service;

import com.calidad.gastos.model.Categoria;
import com.calidad.gastos.model.TipoTransaccion;
import com.calidad.gastos.model.Transaccion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GeneradorResumen {

    private final GestorTransacciones gestorTransacciones;

    public GeneradorResumen(GestorTransacciones gestorTransacciones) {
        this.gestorTransacciones = gestorTransacciones;
    }

    public Map<Categoria, BigDecimal> resumenGastosPorCategoria() {
        Map<Categoria, BigDecimal> resumen = new HashMap<>();
        for (Transaccion transaccion : gestorTransacciones.obtenerTransacciones()) {
            if (transaccion.getTipo() == TipoTransaccion.GASTO) {
                resumen.merge(transaccion.getCategoria(), transaccion.getMonto(), BigDecimal::add);
            }
        }
        return resumen;
    }

    public BigDecimal balanceActual() {
        return gestorTransacciones.calcularBalance();
    }
}