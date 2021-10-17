package com.equipo22.agenda.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equipo22.agenda.R

class TareasRecyclerAdapter(
    private val context: Context,
    private val tareas: MutableList<Tarea>,
    private val clickListener: (Tarea) -> Unit
    ) : RecyclerView.Adapter<TareasRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.txtTitle)
        private val dateHour = view.findViewById<TextView>(R.id.txtDateHour)

        fun bind(tarea: Tarea, context: Context) {
            title.text = tarea.titulo
            dateHour.text = "${tarea.fecha}\t\t\t${tarea.hora}"
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

    fun removeItem(tarea: Tarea) {
        tareas.let {
            val position = it.indexOf(tarea)
            tareas.remove(tarea)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tareas.size)
        }
    }
}