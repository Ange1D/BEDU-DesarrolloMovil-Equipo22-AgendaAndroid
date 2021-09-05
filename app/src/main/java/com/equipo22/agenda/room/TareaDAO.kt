package com.equipo22.agenda.room

import androidx.room.*

@Dao
interface TareaDAO {
    @Insert
    fun insertTarea(tarea: Tarea)

    @Update
    fun updateTarea(tarea: Tarea)

    @Query ("DELETE FROM Tarea WHERE id = :id")
    fun removeVehicleById(id: Int)

    @Query ("SELECT * FROM Tarea")
    fun getAllTareas(): List<Tarea>
}