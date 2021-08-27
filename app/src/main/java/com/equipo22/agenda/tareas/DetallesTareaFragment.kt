package com.equipo22.agenda.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.databinding.FragmentDetallesTareaBinding

class DetallesTareaFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetallesTareaBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "Detalles"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = true
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = true
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false


        binding.txtviewTitulo.text = "${binding.txtviewTitulo.text}: ${TareaManagementActivity.tareaSeleccionada.titulo}"
        binding.txtviewFecha.text = "${binding.txtviewFecha.text}: ${TareaManagementActivity.tareaSeleccionada.fecha}"
        binding.txtviewHora.text = "${binding.txtviewHora.text}: ${TareaManagementActivity.tareaSeleccionada.hora}"
        binding.txtviewDescripcion.text = "${binding.txtviewDescripcion.text}: ${TareaManagementActivity.tareaSeleccionada.descripcion}"
        binding.txtviewTareaPrevia.text = "${binding.txtviewTareaPrevia.text}: ${TareaManagementActivity.tareaSeleccionada.tareaPrevia}"
        binding.txtviewFrecuencia.text = "${binding.txtviewFrecuencia.text}: ${TareaManagementActivity.tareaSeleccionada.frecuencia}"
        binding.txtviewPrioridad.text = "${binding.txtviewPrioridad.text}: ${TareaManagementActivity.tareaSeleccionada.prioridad}"
        binding.txtviewEstado.text = "${binding.txtviewEstado.text}: ${TareaManagementActivity.tareaSeleccionada.estado}"

        return view
    }
}