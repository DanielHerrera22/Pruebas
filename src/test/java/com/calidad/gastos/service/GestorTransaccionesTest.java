package com.calidad.gastos.service;

import com.calidad.gastos.exception.SaldoInsuficienteException;
import com.calidad.gastos.model.Categoria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GestorTransaccionesTest {

    private GestorTransacciones gestor;
    private Categoria salario;
    private Categoria comida;

    @BeforeEach
    void setUp() {
        gestor = new GestorTransacciones();
        salario = new Categoria("Salario");
        comida = new Categoria("Comida");
    }

    // ---------- HAPPY PATH: pruebas simples ----------

    @Test
    @DisplayName("Registrar un ingreso aumenta el balance correctamente")
    void registrarIngreso_actualizaBalance() {
        // Arrange
        BigDecimal monto = new BigDecimal("500");

        // Act
        gestor.registrarIngreso(monto, salario, "Pago mensual", LocalDate.now());

        // Assert
        assertEquals(new BigDecimal("500"), gestor.calcularBalance());
    }

    @Test
    @DisplayName("Registrar un gasto con saldo suficiente reduce el balance")
    void registrarGasto_conSaldoSuficiente_actualizaBalance() {
        // Arrange
        gestor.registrarIngreso(new BigDecimal("500"), salario, "Pago mensual", LocalDate.now());

        // Act
        gestor.registrarGasto(new BigDecimal("200"), comida, "Mercado", LocalDate.now());

        // Assert
        assertEquals(new BigDecimal("300"), gestor.calcularBalance());
    }

    // ---------- EXCEPTIONAL PATH ----------

    @Test
    @DisplayName("Registrar un gasto mayor al saldo disponible lanza SaldoInsuficienteException")
    void registrarGasto_sinSaldoSuficiente_lanzaExcepcion() {
        // Arrange
        gestor.registrarIngreso(new BigDecimal("100"), salario, "Pago parcial", LocalDate.now());

        // Act & Assert
        assertThrows(SaldoInsuficienteException.class, () ->
                gestor.registrarGasto(new BigDecimal("500"), comida, "Compra grande", LocalDate.now())
        );
    }

    @Test
    @DisplayName("Registrar un ingreso con monto negativo lanza IllegalArgumentException")
    void registrarIngreso_conMontoInvalido_lanzaExcepcion() {
        // Arrange
        BigDecimal montoInvalido = new BigDecimal("-50");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                gestor.registrarIngreso(montoInvalido, salario, "Ingreso inválido", LocalDate.now())
        );
    }

    // ---------- PRUEBA AVANZADA ----------

    @Test
    @DisplayName("El total gastado por categoría solo suma transacciones de esa categoría")
    void totalGastadoPorCategoria_soloConsideraLaCategoriaIndicada() {
        // Arrange
        Categoria transporte = new Categoria("Transporte");
        gestor.registrarIngreso(new BigDecimal("1000"), salario, "Pago mensual", LocalDate.now());
        gestor.registrarGasto(new BigDecimal("150"), comida, "Mercado", LocalDate.now());
        gestor.registrarGasto(new BigDecimal("50"), comida, "Restaurante", LocalDate.now());
        gestor.registrarGasto(new BigDecimal("80"), transporte, "Gasolina", LocalDate.now());

        // Act
        BigDecimal totalComida = gestor.totalGastadoPorCategoria(comida);
        BigDecimal totalTransporte = gestor.totalGastadoPorCategoria(transporte);

        // Assert
        assertEquals(new BigDecimal("200"), totalComida);
        assertEquals(new BigDecimal("80"), totalTransporte);
    }

    // ---------- PERFORMANCE UNIT TEST ----------

    @Test
    @DisplayName("Registrar 10000 transacciones se completa en menos de 1 segundo")
    void registrarMuchasTransacciones_esRapido() {
        // Arrange
        int cantidad = 10_000;

        // Act & Assert
        assertTimeout(Duration.ofSeconds(1), () -> {
            for (int i = 0; i < cantidad; i++) {
                gestor.registrarIngreso(new BigDecimal("10"), salario, "Ingreso " + i, LocalDate.now());
            }
        });
    }
}