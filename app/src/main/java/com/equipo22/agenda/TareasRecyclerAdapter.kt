package com.equipo22.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TareasRecyclerAdapter(val tareas: List<String>, val horarios: List<String>) :
    RecyclerView.Adapter<TareasRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.txtTitle)
        private val hour = view.findViewById<TextView>(R.id.txtHour)

        fun bind(tarea: String, horario: String) {
            title.text = tarea
            hour.text = horario
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = tareas[position]
        val horario = horarios[position]

        holder.bind(tarea, horario)
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

}
