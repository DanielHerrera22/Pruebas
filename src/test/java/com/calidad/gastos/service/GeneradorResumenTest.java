package com.calidad.gastos.service;

import com.calidad.gastos.model.Categoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorResumenTest {

    @Test
    @DisplayName("Generar resumen suma los gastos agrupados por categoría")
    void resumenGastosPorCategoria_agrupaYSumaCorrectamente() {

        // Arrange
        GestorTransacciones gestor = new GestorTransacciones();

        Categoria comida = new Categoria("Comida");
        Categoria transporte = new Categoria("Transporte");

        gestor.registrarIngreso(
                new BigDecimal("1000"),
                new Categoria("Salario"),
                "Ingreso",
                LocalDate.now()
        );

        gestor.registrarGasto(
                new BigDecimal("100"),
                comida,
                "Almuerzo",
                LocalDate.now()
        );

        gestor.registrarGasto(
                new BigDecimal("50"),
                comida,
                "Cena",
                LocalDate.now()
        );

        gestor.registrarGasto(
                new BigDecimal("80"),
                transporte,
                "Gasolina",
                LocalDate.now()
        );

        GeneradorResumen generador = new GeneradorResumen(gestor);

        // Act
        Map<Categoria, BigDecimal> resumen =
                generador.resumenGastosPorCategoria();

        // Assert
        assertEquals(new BigDecimal("150"), resumen.get(comida));
        assertEquals(new BigDecimal("80"), resumen.get(transporte));
    }

    @Test
    @DisplayName("Generar resumen sin transacciones devuelve un mapa vacío")
    void resumenGastosPorCategoria_sinTransacciones_devuelveMapaVacio() {

        // Arrange
        GestorTransacciones gestor = new GestorTransacciones();
        GeneradorResumen generador = new GeneradorResumen(gestor);

        // Act
        Map<Categoria, BigDecimal> resumen =
                generador.resumenGastosPorCategoria();

        // Assert
        assertTrue(resumen.isEmpty());
    }

    @Test
    @DisplayName("Obtener balance actual devuelve el balance calculado por el gestor")
    void balanceActual_devuelveBalanceCorrecto() {

        // Arrange
        GestorTransacciones gestor = new GestorTransacciones();

        Categoria salario = new Categoria("Salario");
        Categoria comida = new Categoria("Comida");

        gestor.registrarIngreso(
                new BigDecimal("1000"),
                salario,
                "Salario",
                LocalDate.now()
        );

        gestor.registrarGasto(
                new BigDecimal("250"),
                comida,
                "Mercado",
                LocalDate.now()
        );

        GeneradorResumen generador = new GeneradorResumen(gestor);

        // Act
        BigDecimal balance = generador.balanceActual();

        // Assert
        assertEquals(new BigDecimal("750"), balance);
    }
}