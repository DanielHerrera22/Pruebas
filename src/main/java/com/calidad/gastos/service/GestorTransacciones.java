package com.calidad.gastos.service;

import com.calidad.gastos.exception.SaldoInsuficienteException;
import com.calidad.gastos.model.Categoria;
import com.calidad.gastos.model.Transaccion;
import com.calidad.gastos.model.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorTransacciones {

    private final List<Transaccion> transacciones = new ArrayList<>();

    public Transaccion registrarIngreso(BigDecimal monto, Categoria categoria,
                                         String descripcion, LocalDate fecha) {
        Transaccion transaccion = new Transaccion(TipoTransaccion.INGRESO, monto, categoria, descripcion, fecha);
        transacciones.add(transaccion);
        return transaccion;
    }

    public Transaccion registrarGasto(BigDecimal monto, Categoria categoria,
                                       String descripcion, LocalDate fecha) {
        BigDecimal saldoActual = calcularBalance();
        if (monto != null && saldoActual.compareTo(monto) < 0) {
            throw new SaldoInsuficienteException(saldoActual, monto);
        }
        Transaccion transaccion = new Transaccion(TipoTransaccion.GASTO, monto, categoria, descripcion, fecha);
        transacciones.add(transaccion);
        return transaccion;
    }

    public BigDecimal calcularBalance() {
        return totalPorTipo(TipoTransaccion.INGRESO).subtract(totalPorTipo(TipoTransaccion.GASTO));
    }

    public BigDecimal totalPorTipo(TipoTransaccion tipo) {
        return transacciones.stream()
                .filter(t -> t.getTipo() == tipo)
                .map(Transaccion::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalGastadoPorCategoria(Categoria categoria) {
        return transacciones.stream()
                .filter(t -> t.getTipo() == TipoTransaccion.GASTO)
                .filter(t -> t.getCategoria().equals(categoria))
                .map(Transaccion::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaccion> obtenerTransacciones() {
        return Collections.unmodifiableList(transacciones);
    }
}