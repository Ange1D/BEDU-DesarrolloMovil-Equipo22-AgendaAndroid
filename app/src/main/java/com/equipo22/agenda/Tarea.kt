package com.equipo22.agenda

data class Tarea(
    var titulo: String,
    var fechaInicio: String,
    var horaInicio: String,
    var fechaFin: String,
    var horaFin: String,
    var objetivo: String,
    var descripcion: String,
    var requisitos: String,
    var tareaPrevia: String,
    var frecuencia: String,
    var prioridad: Int
)
