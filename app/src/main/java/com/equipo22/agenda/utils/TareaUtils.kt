package com.equipo22.agenda.utils

import com.equipo22.agenda.room.Tarea

internal fun getNumberOfTareas(tareas: List<Tarea>?):Int {
    return tareas?.size?:0
}

internal fun finishedTareasPercentage(tareas: List<Tarea>?): Float {
    return if (tareas.isNullOrEmpty()) {
        0F
    } else {
        val finishedTareas = tareas!!.count { !it.estado }
        val totalTareas = tareas?.size

        ((totalTareas - finishedTareas) / totalTareas.toFloat()) * 100F
    }
}