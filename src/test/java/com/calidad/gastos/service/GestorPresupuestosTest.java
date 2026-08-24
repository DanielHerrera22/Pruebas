package com.calidad.gastos.service;

import com.calidad.gastos.exception.PresupuestoInvalidoException;
import com.calidad.gastos.model.Categoria;
import com.calidad.gastos.model.Presupuesto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GestorPresupuestosTest {

    private GestorPresupuestos gestor;
    private Categoria comida;

    @BeforeEach
    void setUp() {
        gestor = new GestorPresupuestos();
        comida = new Categoria("Comida");
    }

    // ---------- HAPPY PATH ----------

    @Test
    @DisplayName("Definir un presupuesto válido lo deja disponible para consulta")
    void definirPresupuesto_conDatosValidos_seGuardaCorrectamente() {
        // Arrange
        BigDecimal limite = new BigDecimal("300");

        // Act
        gestor.definirPresupuesto(comida, limite);

        // Assert
        Optional<Presupuesto> resultado = gestor.obtenerPresupuesto(comida);
        assertTrue(resultado.isPresent());
        assertEquals(limite, resultado.get().getLimite());
    }

    @Test
    @DisplayName("Un gasto por debajo del límite no excede el presupuesto")
    void estaExcedido_conGastoMenorAlLimite_devuelveFalse() {
        // Arrange
        gestor.definirPresupuesto(comida, new BigDecimal("300"));

        // Act
        boolean excedido = gestor.estaExcedido(comida, new BigDecimal("200"));

        // Assert
        assertFalse(excedido);
    }

    // ---------- EXCEPTIONAL PATH ----------

    @Test
    @DisplayName("Definir un presupuesto con límite negativo lanza PresupuestoInvalidoException")
    void definirPresupuesto_conLimiteNegativo_lanzaExcepcion() {
        // Arrange
        BigDecimal limiteInvalido = new BigDecimal("-50");

        // Act & Assert
        assertThrows(PresupuestoInvalidoException.class, () ->
                gestor.definirPresupuesto(comida, limiteInvalido)
        );
    }

    @Test
    @DisplayName("Definir un presupuesto con límite cero lanza PresupuestoInvalidoException")
    void definirPresupuesto_conLimiteCero_lanzaExcepcion() {
        // Act & Assert
        assertThrows(PresupuestoInvalidoException.class, () ->
                gestor.definirPresupuesto(comida, BigDecimal.ZERO)
        );
    }

    @Test
    @DisplayName("Definir un presupuesto con categoría nula lanza PresupuestoInvalidoException")
    void definirPresupuesto_conCategoriaNula_lanzaExcepcion() {
        // Act & Assert
        assertThrows(PresupuestoInvalidoException.class, () ->
                gestor.definirPresupuesto(null, new BigDecimal("100"))
        );
    }

    // ---------- PRUEBA AVANZADA ----------

    @Test
    @DisplayName("Un gasto que supera el límite marca el presupuesto como excedido")
    void estaExcedido_conGastoMayorAlLimite_devuelveTrue() {
        // Arrange
        gestor.definirPresupuesto(comida, new BigDecimal("150"));
        BigDecimal gastoAcumulado = new BigDecimal("200");

        // Act
        boolean excedido = gestor.estaExcedido(comida, gastoAcumulado);

        // Assert
        assertTrue(excedido);
    }

    @Test
    @DisplayName("Consultar el presupuesto de una categoría sin presupuesto definido no falla y no excede")
    void estaExcedido_sinPresupuestoDefinido_devuelveFalse() {
        // Arrange
        Categoria transporte = new Categoria("Transporte");

        // Act
        boolean excedido = gestor.estaExcedido(transporte, new BigDecimal("1000"));

        // Assert
        assertFalse(excedido);
        assertTrue(gestor.obtenerPresupuesto(transporte).isEmpty());
    }
}