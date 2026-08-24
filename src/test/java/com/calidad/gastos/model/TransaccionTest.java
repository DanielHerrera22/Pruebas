package com.calidad.gastos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransaccionTest {

    @Test
    @DisplayName("Crear una transacción con datos válidos conserva correctamente sus datos")
    void crearTransaccion_conDatosValidos_conservaDatos() {

        // Arrange
        TipoTransaccion tipo = TipoTransaccion.GASTO;
        BigDecimal monto = new BigDecimal("50000");
        Categoria categoria = new Categoria("Comida");
        String descripcion = "Almuerzo";
        LocalDate fecha = LocalDate.of(2026, 8, 23);

        // Act
        Transaccion transaccion = new Transaccion(
                tipo,
                monto,
                categoria,
                descripcion,
                fecha
        );

        // Assert
        assertEquals(tipo, transaccion.getTipo());
        assertEquals(monto, transaccion.getMonto());
        assertEquals(categoria, transaccion.getCategoria());
        assertEquals(descripcion, transaccion.getDescripcion());
        assertEquals(fecha, transaccion.getFecha());
    }

    @Test
    @DisplayName("Crear una transacción con monto negativo lanza IllegalArgumentException")
    void crearTransaccion_conMontoNegativo_lanzaExcepcion() {

        // Arrange
        Categoria categoria = new Categoria("Comida");
        BigDecimal montoInvalido = new BigDecimal("-100");

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new Transaccion(
                        TipoTransaccion.GASTO,
                        montoInvalido,
                        categoria,
                        "Compra inválida",
                        LocalDate.now()
                )
        );
    }
}