package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class VerTareasActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var recyclerTareas: RecyclerView
    val tareas = listOf<Tarea>(
        Tarea("Tarea 01", "09:00 - 10:00 hrs."),
        Tarea("Tarea 02", "10:00 - 11:00 hrs."),
        Tarea("Tarea 03", "11:00 - 12:00 hrs."),
        Tarea("Tarea 04", "12:00 - 13:00 hrs."),
        Tarea("Tarea 05", "13:00 - 14:00 hrs.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_tareas)

        recyclerTareas = findViewById(R.id.recyclerTareas)

        recyclerTareas.adapter = TareasRecyclerAdapter(tareas, this)
    }

    override fun onItemClick(tarea: Tarea) {
        val intent = Intent(this, EditarTareaActivity::class.java)
        intent.putExtra("Titulo", tarea.titulo)
        intent.putExtra("Horario", tarea.horario)
        startActivity(intent)
    }
}