package com.equipo22.agenda.room

interface ItemListener {
    fun onEdit(tarea: Tarea)

    fun onDelete(tarea: Tarea)
}