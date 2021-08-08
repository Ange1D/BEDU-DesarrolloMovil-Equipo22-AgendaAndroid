package com.equipo22.agenda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TareasRecyclerAdapter(
    private val context: Context,
    private val tareas: MutableList<Tarea>,
    private val clickListener: (Tarea) -> Unit
    ) : RecyclerView.Adapter<TareasRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.txtTitle)
        private val description = view.findViewById<TextView>(R.id.txtDescription)

        fun bind(tarea: Tarea, context: Context) {
            title.text = tarea.titulo
            description.text = tarea.descripcion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = tareas[position]
        holder.bind(tarea, context)

        holder.view.setOnClickListener { clickListener(tarea) }
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

}