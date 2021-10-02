package com.equipo22.agenda.utils

import com.equipo22.agenda.room.Tarea
import org.junit.Test

import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat

class TareaUtilsKtTest {

    @Test
    fun getNumberOfTareas_empty_returnsZero() {
        val tareas = listOf<Tarea>()
        val result = getNumberOfTareas(tareas)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun finishedTareasPercentage_four_returnsSeventyFive() {
        val tareas = listOf(
            Tarea(
                titulo = "Tarea de prueba 01",
                fecha = "02/Octubre/2021",
                hora = "12:00 hrs.",
                tareaPrevia = "Ninguna",
                repetir = "Nunca",
                descripcion = "Vacía",
                estado = true
            ),
            Tarea(
                titulo = "Tarea de prueba 02",
                fecha = "02/Octubre/2021",
                hora = "13:00 hrs.",
                tareaPrevia = "Ninguna",
                repetir = "Nunca",
                descripcion = "Vacía",
                estado = false
            ),
            Tarea(
                titulo = "Tarea de prueba 03",
                fecha = "02/Octubre/2021",
                hora = "14:00 hrs.",
                tareaPrevia = "Ninguna",
                repetir = "Nunca",
                descripcion = "Vacía",
                estado = true
            ),
            Tarea(
                titulo = "Tarea de prueba 04",
                fecha = "02/Octubre/2021",
                hora = "15:00 hrs.",
                tareaPrevia = "Ninguna",
                repetir = "Nunca",
                descripcion = "Vacía",
                estado = true
            )
        )
        val result = finishedTareasPercentage(tareas)
        assertThat(result).isEqualTo(75F)
    }
}