package com.equipo22.agenda.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo22.agenda.*
import com.equipo22.agenda.databinding.FragmentVerListadoBinding

class VerListadoFragment: Fragment() {

    private val horizontalLinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private lateinit var mAdapter: TareasRecyclerAdapter
    private var listener: (Tarea) -> Unit = {
        TareaManagementActivity.tareaSeleccionada = it
        TareaManagementActivity.tareaSeleccionadaIndex = TareaManagementActivity.tareas.indexOf(it)
        requireActivity().title = getString(R.string.titleDetalles)
        (activity as TareaManagementActivity).navigateTo(DetallesTareaFragment(), false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVerListadoBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "VerListado"

        mAdapter = TareasRecyclerAdapter(requireActivity(), TareaManagementActivity.tareas, listener)

        binding.recyclerTareas.layoutManager = horizontalLinearLayoutManager
        binding.recyclerTareas.adapter = mAdapter


        return view
    }

}