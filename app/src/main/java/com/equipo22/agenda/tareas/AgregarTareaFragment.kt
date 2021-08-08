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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AgregarTareaFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_agregar_tarea, container, false)
        TareaManagementActivity.SHOWING_FRAGMENT = "AgregarTarea"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        var estado = ""
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.fecha))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            when {
                txtFecha.hasFocus() -> {
                    val fechaSeleccionada = setFecha(datePicker.headerText)

                    txtFecha.setText(
                        fechaSeleccionada
                    )
                }
                else -> {  }
            }
        }
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(resources.getString(R.string.hora))
                .build()
        timePicker.addOnPositiveButtonClickListener {
            when {
                txtHora.hasFocus() -> {
                    txtHora.setText(setHora(timePicker.hour.toString(), timePicker.minute.toString()))
                }
                else -> {  }
            }
        }

        txtTitulo = view.findViewById(R.id.txtTitulo)
        txtFecha = view.findViewById(R.id.txtFecha)
        txtHora = view.findViewById(R.id.txtHora)
        txtDescripcion = view.findViewById(R.id.txtDescripcion)
        txtTareaPrevia = view.findViewById(R.id.txtTareaPrevia)
        txtFrecuencia = view.findViewById(R.id.txtFrecuencia)
        txtPrioridad = view.findViewById(R.id.txtPrioridad)

        txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.titulosTareas))
        txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.frecuencia))
        txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.prioridad))
        txtFecha.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        txtFecha.setOnClickListener {
            datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        txtHora.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }
        txtHora.setOnClickListener {
            timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }

        rbPendiente = view.findViewById(R.id.rb_pendiente)
        rbFinalizada = view.findViewById(R.id.rb_finalizado)
        bttnSave = view.findViewById(R.id.btnSave)
        bttnCancel = view.findViewById(R.id.btnCancel)
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
                TareaManagementActivity.tareas.add(
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
                TareaManagementActivity.titulosTareas.add(txtTitulo.text.toString())
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

    fun setFecha(fecha: String): String{
        var nuevaFecha:String = fecha
        if (fecha.toCharArray().size < 11) {
            nuevaFecha = "0$nuevaFecha"
        }

        val dia = nuevaFecha.substring(0, 2)
        var mes = nuevaFecha.substring(3, 6)
        when (mes) {
            "ene" -> mes = "Enero"
            "feb" -> mes = "Febrero"
            "mar" -> mes = "Marzo"
            "abr" -> mes = "Abril"
            "may" -> mes = "Mayo"
            "jun" -> mes = "Junio"
            "jul" -> mes = "Julio"
            "ago" -> mes = "Agosto"
            "sep" -> mes = "Septiembre"
            "oct" -> mes = "Octubre"
            "nov" -> mes = "Noviembre"
            "dic" -> mes = "Diciembre"
        }
        val anno = nuevaFecha.substring(7)
        nuevaFecha = "$dia/$mes/$anno"

        return nuevaFecha
    }

    fun setHora(hora: String, minuto: String): String {
        var horaReal = ""
        var nuevaHora = hora
        var nuevoMinuto = minuto
        if (hora.toCharArray().size<2)
            nuevaHora = "0$hora"
        if (minuto.toCharArray().size<2)
            nuevoMinuto = "0$minuto"

        horaReal = "$nuevaHora:$nuevoMinuto hrs."

        return horaReal
    }
}