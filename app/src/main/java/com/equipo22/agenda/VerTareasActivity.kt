package com.equipo22.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class VerTareasActivity : AppCompatActivity() {

    private lateinit var recyclerTareas: RecyclerView
    val tareas = listOf<String>("Tarea 01", "Tarea 02", "Tarea 03", "Tarea 04", "Tarea 05")
    val horarios =listOf<String>("09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_tareas)

        recyclerTareas = findViewById(R.id.recyclerTareas)

        recyclerTareas.adapter = TareasRecyclerAdapter(tareas, horarios)
    }
}