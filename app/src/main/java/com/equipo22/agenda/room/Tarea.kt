package com.equipo22.agenda.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Titulo") val titulo: String = "Tarea genérica",
    @ColumnInfo(name = "Fecha") val fecha: String = "",
    @ColumnInfo(name = "Hora") val hora: String = "",
    @ColumnInfo(name = "Descripcion") val descripcion: String = "",
    @ColumnInfo(name = "Tarea_Previa") val tareaPrevia: String = "Ninguna",
    @ColumnInfo(name = "Repetir") val repetir: String = "Nunca",
    @ColumnInfo(name = "Prioridad") val prioridad: Int = 5,
    @ColumnInfo(name = "Estado") val estado: Boolean = false
)
