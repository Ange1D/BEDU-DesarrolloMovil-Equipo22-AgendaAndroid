package com.equipo22.agenda.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.google.android.material.textview.MaterialTextView

class DetallesTareaFragment: Fragment() {
    private lateinit var txtviewTitulo: MaterialTextView
    private lateinit var txtviewFecha: MaterialTextView
    private lateinit var txtviewHora: MaterialTextView
    private lateinit var txtviewDescripcion: MaterialTextView
    private lateinit var txtviewTareaPrevia: MaterialTextView
    private lateinit var txtviewFrecuencia: MaterialTextView
    private lateinit var txtviewPrioridad: MaterialTextView
    private lateinit var txtviewEstado: MaterialTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalles_tarea, container, false)
        TareaManagementActivity.SHOWING_FRAGMENT = "Detalles"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = true
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = true
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false

        txtviewTitulo = view.findViewById(R.id.txtviewTitulo)
        txtviewFecha = view.findViewById(R.id.txtviewFecha)
        txtviewHora = view.findViewById(R.id.txtviewHora)
        txtviewDescripcion = view.findViewById(R.id.txtviewDescripcion)
        txtviewTareaPrevia = view.findViewById(R.id.txtviewTareaPrevia)
        txtviewFrecuencia = view.findViewById(R.id.txtviewFrecuencia)
        txtviewPrioridad = view.findViewById(R.id.txtviewPrioridad)
        txtviewEstado = view.findViewById(R.id.txtviewEstado)

        txtviewTitulo.text = "${txtviewTitulo.text}: ${TareaManagementActivity.tareaSeleccionada.titulo}"
        txtviewFecha.text = "${txtviewFecha.text}: ${TareaManagementActivity.tareaSeleccionada.fecha}"
        txtviewHora.text = "${txtviewHora.text}: ${TareaManagementActivity.tareaSeleccionada.hora}"
        txtviewDescripcion.text = "${txtviewDescripcion.text}: ${TareaManagementActivity.tareaSeleccionada.descripcion}"
        txtviewTareaPrevia.text = "${txtviewTareaPrevia.text}: ${TareaManagementActivity.tareaSeleccionada.tareaPrevia}"
        txtviewFrecuencia.text = "${txtviewFrecuencia.text}: ${TareaManagementActivity.tareaSeleccionada.frecuencia}"
        txtviewPrioridad.text = "${txtviewPrioridad.text}: ${TareaManagementActivity.tareaSeleccionada.prioridad}"
        txtviewEstado.text = "${txtviewEstado.text}: ${TareaManagementActivity.tareaSeleccionada.estado}"

        return view
    }
}