package com.equipo22.agenda.tareas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.equipo22.agenda.R
import com.equipo22.agenda.Tarea
import com.equipo22.agenda.TareasRecyclerAdapter

class VerListadoFragment: Fragment() {
    private lateinit var recyclerTareas: RecyclerView
    private val horizontalLinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private lateinit var mAdapter: TareasRecyclerAdapter
    private var listener: (Tarea) -> Unit = {
        TareaManagementActivity.tareaSeleccionada = it
        TareaManagementActivity.tareaSeleccionadaIndex = TareaManagementActivity.tareas.indexOf(it)
        (activity as TareaManagementActivity).navigateTo(DetallesTareaFragment(), false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ver_listado, container, false)
        TareaManagementActivity.SHOWING_FRAGMENT = "VerListado"

        mAdapter = TareasRecyclerAdapter(requireActivity(), TareaManagementActivity.tareas, listener)
        recyclerTareas = view.findViewById(R.id.recyclerTareas)
        recyclerTareas.layoutManager = horizontalLinearLayoutManager
        recyclerTareas.adapter = mAdapter

        return view
    }
}