package com.calidad.gastos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PresupuestoTest {

    @Test
    @DisplayName("Crear un presupuesto con límite cero lanza IllegalArgumentException")
    void crearPresupuesto_conLimiteCero_lanzaExcepcion() {

        // Arrange
        Categoria categoria = new Categoria("Comida");
        BigDecimal limiteInvalido = BigDecimal.ZERO;

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Presupuesto(categoria, limiteInvalido)
        );
    }
}