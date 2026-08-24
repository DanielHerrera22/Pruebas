package com.calidad.gastos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    @DisplayName("Crear una categoría con nombre válido elimina espacios innecesarios")
    void crearCategoria_conNombreValido_recortaEspacios() {

        // Arrange
        String nombre = "  Comida  ";

        // Act
        Categoria categoria = new Categoria(nombre);

        // Assert
        assertEquals("Comida", categoria.getNombre());
    }
}