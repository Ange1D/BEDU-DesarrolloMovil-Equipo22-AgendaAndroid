package com.equipo22.agenda.tareas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.databinding.FragmentAgregarTareaBinding
import com.equipo22.agenda.room.TareaDB
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.concurrent.Executors

class AgregarTareaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAgregarTareaBinding.inflate(layoutInflater)
        val view = binding.root

        binding.rbPendiente.isChecked = true
        binding.rbFinalizado.isChecked = false
        TareaManagementActivity.SHOWING_FRAGMENT = "AgregarTarea"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false
        var estado = false
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.fecha))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            val fechaSeleccionada = setFecha(datePicker.headerText)
            binding.txtFecha.setText(fechaSeleccionada)
        }
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(resources.getString(R.string.hora))
                .build()
        timePicker.addOnPositiveButtonClickListener {
            binding.txtHora.setText(setHora(timePicker.hour.toString(), timePicker.minute.toString()))
        }


        binding.txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.titulosTareas))
        binding.txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.frecuencia))
        binding.txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.prioridad))
        binding.txtFecha.setOnClickListener {
            datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        binding.txtHora.setOnClickListener {
            timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }

        binding.rbPendiente.setOnClickListener {
            if (binding.rbPendiente.isChecked)
                estado = false
        }
        binding.rbFinalizado.setOnClickListener {
            if (binding.rbFinalizado.isChecked)
                estado = true
        }

        binding.btnSave.setOnClickListener {
            if (
                binding.txtTitulo.text.toString().isNotEmpty() &&
                binding.txtFecha.text.toString().isNotEmpty() &&
                binding.txtHora.text.toString().isNotEmpty()
            ) {
                val tarea = Tarea(
                    titulo = binding.txtTitulo.text.toString(),
                    fecha = binding.txtFecha.text.toString(),
                    hora = binding.txtHora.text.toString(),
                    descripcion = if (binding.txtDescripcion.text.toString().isEmpty()) "" else binding.txtDescripcion.text.toString(),
                    tareaPrevia = if (binding.txtTareaPrevia.text.toString().isEmpty()) "Ninguna" else binding.txtTareaPrevia.text.toString(),
                    repetir = if (binding.txtFrecuencia.text.toString().isEmpty()) "Nunca" else binding.txtFrecuencia.text.toString(),
                    prioridad = if (binding.txtPrioridad.text.toString().isEmpty()) 5 else binding.txtPrioridad.text.toString().toInt(),
                    estado = estado
                )
                Executors.newSingleThreadExecutor().execute {
                    TareaDB.getInstance(requireContext())
                        ?.tareaDAO()
                        ?.insertTarea(tarea)

                    Handler(Looper.getMainLooper()).post {
                        (activity as TareaManagementActivity).onBackPressed()
                    }
                }
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