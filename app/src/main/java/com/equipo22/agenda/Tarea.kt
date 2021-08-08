package com.equipo22.agenda

data class Tarea(
    val titulo: String,
    val fecha: String,
    val hora: String,
    val descripcion: String,
    val tareaPrevia: String,
    val frecuencia: String,
    val prioridad: Int
)
