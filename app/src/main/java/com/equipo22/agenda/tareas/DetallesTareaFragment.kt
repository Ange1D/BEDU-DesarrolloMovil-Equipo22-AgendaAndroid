package com.equipo22.agenda.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.equipo22.agenda.R
import com.equipo22.agenda.PrincipalActivity
import com.equipo22.agenda.databinding.FragmentDetallesTareaBinding

class DetallesTareaFragment: Fragment() {
    val args: DetallesTareaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetallesTareaBinding.inflate(layoutInflater)
        val view = binding.root

        PrincipalActivity.SHOWING_FRAGMENT = "Detalles"
        PrincipalActivity.tareasMenu.findItem(R.id.add_dest).isVisible = false
        PrincipalActivity.tareasMenu.findItem(R.id.edit_dest).isVisible = true
        PrincipalActivity.tareasMenu.findItem(R.id.action_delete).isVisible = true

        binding.txtviewTitulo.text = "${binding.txtviewTitulo.text}: ${args.selectedTask.titulo}"
        binding.txtviewFecha.text = "${binding.txtviewFecha.text}: ${args.selectedTask.fecha}"
        binding.txtviewHora.text = "${binding.txtviewHora.text}: ${args.selectedTask.hora}"
        binding.txtviewDescripcion.text = "${binding.txtviewDescripcion.text}: ${args.selectedTask.descripcion}"
        binding.txtviewTareaPrevia.text = "${binding.txtviewTareaPrevia.text}: ${args.selectedTask.tareaPrevia}"
        binding.txtviewFrecuencia.text = "${binding.txtviewFrecuencia.text}: ${args.selectedTask.repetir}"
        binding.txtviewPrioridad.text = "${binding.txtviewPrioridad.text}: ${args.selectedTask.prioridad}"
        binding.txtviewEstado.text = "${binding.txtviewEstado.text}: ${
            if (args.selectedTask.estado)
                getString(R.string.estadoF)
            else
                getString(R.string.estadoP)
        }"

        return view
    }
}