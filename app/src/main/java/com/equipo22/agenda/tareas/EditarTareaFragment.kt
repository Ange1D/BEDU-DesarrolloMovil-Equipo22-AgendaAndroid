package com.equipo22.agenda.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.Tarea
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class EditarTareaFragment : Fragment() {
    private lateinit var txtTitulo: TextInputEditText
    private lateinit var txtFecha: TextInputEditText
    private lateinit var txtHora: TextInputEditText
    private lateinit var txtDescripcion: TextInputEditText
    private lateinit var txtTareaPrevia: AutoCompleteTextView
    private lateinit var txtFrecuencia: AutoCompleteTextView
    private lateinit var txtPrioridad: AutoCompleteTextView
    private lateinit var rbPendiente: RadioButton
    private lateinit var rbFinalizada: RadioButton
    private lateinit var bttnSave: Button
    private lateinit var bttnCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_tarea, container, false)
        TareaManagementActivity.SHOWING_FRAGMENT = "EditarTarea"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        var estado = ""

        txtTitulo = view.findViewById(R.id.txtTitulo)
        txtFecha = view.findViewById(R.id.txtFecha)
        txtHora = view.findViewById(R.id.txtHora)
        txtDescripcion = view.findViewById(R.id.txtDescripcion)
        txtTareaPrevia = view.findViewById(R.id.txtTareaPrevia)
        txtFrecuencia = view.findViewById(R.id.txtFrecuencia)
        txtPrioridad = view.findViewById(R.id.txtPrioridad)
        rbPendiente = view.findViewById(R.id.rb_pendiente)
        rbFinalizada = view.findViewById(R.id.rb_finalizado)
        bttnSave = view.findViewById(R.id.btnSave)
        bttnCancel = view.findViewById(R.id.btnCancel)

        txtTitulo.setText(TareaManagementActivity.tareaSeleccionada.titulo)
        txtFecha.setText(TareaManagementActivity.tareaSeleccionada.fecha)
        txtHora.setText(TareaManagementActivity.tareaSeleccionada.hora)
        txtDescripcion.setText(TareaManagementActivity.tareaSeleccionada.descripcion)
        txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.titulosTareas))
        txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.frecuencia))
        txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.prioridad))

        if (TareaManagementActivity.tareaSeleccionada.estado == resources.getString(R.string.estadoP)) {
            rbPendiente.isChecked = true
            estado = resources.getString(R.string.estadoP)
            rbFinalizada.isChecked = false
        } else if (TareaManagementActivity.tareaSeleccionada.estado == resources.getString(R.string.estadoF)) {
            rbPendiente.isChecked = false
            rbFinalizada.isChecked = true
            estado = resources.getString(R.string.estadoF)
        }
        rbPendiente.setOnClickListener {
            estado = resources.getString(R.string.estadoP)
        }
        rbFinalizada.setOnClickListener {
            estado = resources.getString(R.string.estadoF)
        }

        bttnSave.setOnClickListener {
            if (
                txtTitulo.text.toString().isNotEmpty() &&
                txtFecha.text.toString().isNotEmpty() &&
                txtHora.text.toString().isNotEmpty() &&
                txtDescripcion.text.toString().isNotEmpty() &&
                txtTareaPrevia.text.toString().isNotEmpty() &&
                txtFrecuencia.text.toString().isNotEmpty() &&
                txtPrioridad.text.toString().isNotEmpty() &&
                estado != ""
            ) {
                TareaManagementActivity.tareas.set(
                    TareaManagementActivity.tareaSeleccionadaIndex,
                    Tarea(
                        txtTitulo.text.toString(),
                        txtFecha.text.toString(),
                        txtHora.text.toString(),
                        txtDescripcion.text.toString(),
                        txtTareaPrevia.text.toString(),
                        txtFrecuencia.text.toString(),
                        txtPrioridad.text.toString().toInt(),
                        estado
                    )
                )
                TareaManagementActivity.titulosTareas.set(TareaManagementActivity.tareaSeleccionadaIndex, txtTitulo.text.toString())
                (activity as TareaManagementActivity).navigateTo(VerListadoFragment(), false)
            } else {
                MaterialAlertDialogBuilder((activity as TareaManagementActivity))
                    .setTitle(resources.getString(R.string.incomplete_data_title))
                    .setMessage(resources.getString(R.string.incomplete_data_description))
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->

                    }
                    .show()
            }
        }
        bttnCancel.setOnClickListener {
            (activity as TareaManagementActivity).onBackPressed()
        }

        return view
    }
}