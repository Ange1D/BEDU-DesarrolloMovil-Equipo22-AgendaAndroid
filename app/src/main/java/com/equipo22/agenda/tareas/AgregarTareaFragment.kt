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
import com.equipo22.agenda.databinding.FragmentAgregarTareaBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AgregarTareaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAgregarTareaBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "AgregarTarea"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false
        var estado = ""
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.fecha))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            when {
                binding.txtFecha.hasFocus() -> {
                    val fechaSeleccionada = setFecha(datePicker.headerText)

                    binding.txtFecha.setText(
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
                binding.txtHora.hasFocus() -> {
                    binding.txtHora.setText(setHora(timePicker.hour.toString(), timePicker.minute.toString()))
                }
                else -> {  }
            }
        }


        binding.txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.titulosTareas))
        binding.txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.frecuencia))
        binding.txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.prioridad))
        binding.txtFecha.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        binding.txtFecha.setOnClickListener {
            datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        binding.txtHora.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }
        binding.txtHora.setOnClickListener {
            timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }


        binding.rbPendiente.setOnClickListener {
            estado = resources.getString(R.string.estadoP)
        }
        binding.rbFinalizado.setOnClickListener {
            estado = resources.getString(R.string.estadoF)
        }

        binding.btnSave.setOnClickListener {
            if (
                binding.txtTitulo.text.toString().isNotEmpty() &&
                binding.txtFecha.text.toString().isNotEmpty() &&
                binding.txtHora.text.toString().isNotEmpty() &&
                binding.txtDescripcion.text.toString().isNotEmpty() &&
                binding.txtTareaPrevia.text.toString().isNotEmpty() &&
                binding.txtFrecuencia.text.toString().isNotEmpty() &&
                binding.txtPrioridad.text.toString().isNotEmpty() &&
                estado != ""
            ) {
                TareaManagementActivity.tareas.add(
                    Tarea(
                        binding.txtTitulo.text.toString(),
                        binding.txtFecha.text.toString(),
                        binding.txtHora.text.toString(),
                        binding.txtDescripcion.text.toString(),
                        binding.txtTareaPrevia.text.toString(),
                        binding.txtFrecuencia.text.toString(),
                        binding.txtPrioridad.text.toString().toInt(),
                        estado
                    )
                )
                TareaManagementActivity.titulosTareas.add(binding.txtTitulo.text.toString())
                (activity as TareaManagementActivity).onBackPressed()
            } else {
                MaterialAlertDialogBuilder((activity as TareaManagementActivity))
                    .setTitle(resources.getString(R.string.incomplete_data_title))
                    .setMessage(resources.getString(R.string.incomplete_data_description))
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->

                    }
                    .show()
            }
        }
        binding.btnCancel.setOnClickListener {
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