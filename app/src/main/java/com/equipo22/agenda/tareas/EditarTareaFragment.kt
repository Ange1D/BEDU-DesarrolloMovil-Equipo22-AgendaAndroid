package com.equipo22.agenda.tareas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.databinding.FragmentEditarTareaBinding
import com.equipo22.agenda.room.TareaDB
import com.equipo22.agenda.tareas.TareaManagementActivity.Companion.tareaSeleccionada
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

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

        val binding = FragmentEditarTareaBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "EditarTarea"
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
            val fechaSeleccionada = setFecha(it)
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

        txtTitulo = binding.txtTitulo
        txtFecha = binding.txtFecha
        txtHora = binding.txtHora
        txtDescripcion = binding.txtDescripcion
        txtTareaPrevia = binding.txtTareaPrevia
        txtFrecuencia = binding.txtFrecuencia
        txtPrioridad = binding.txtPrioridad
        rbPendiente = binding.rbPendiente
        rbFinalizada = binding.rbFinalizado
        bttnSave = binding.btnSave
        bttnCancel = binding.btnCancel

        txtTitulo.setText(tareaSeleccionada.titulo)
        txtFecha.setText(tareaSeleccionada.fecha)
        txtFecha.setOnClickListener {
            datePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        txtHora.setText(tareaSeleccionada.hora)
        binding.txtHora.setOnClickListener {
            timePicker.show((activity as TareaManagementActivity).supportFragmentManager, resources.getString(R.string.hora))
        }
        txtDescripcion.setText(tareaSeleccionada.descripcion)
        txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.titulosTareas))
        txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.frecuencia))
        txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, TareaManagementActivity.prioridad))

        if (tareaSeleccionada.estado) {
            rbPendiente.isChecked = false
            rbFinalizada.isChecked = true
        } else {
            rbPendiente.isChecked = true
            rbFinalizada.isChecked = false
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
                    id = tareaSeleccionada.id,
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
                        ?.updateTarea(tarea)

                    Handler(Looper.getMainLooper()).post {
                        (activity as TareaManagementActivity).navigateTo(VerListadoFragment(), false)
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

        bttnCancel.setOnClickListener {
            (activity as TareaManagementActivity).onBackPressed()
        }

        return view
    }

    fun setFecha(dateTimeStamp: Long): String{
        val offsetFromUTC = TimeZone.getDefault().getOffset(Date().time) * -1
        val dateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault())

        return (
                dateFormat.format((dateTimeStamp + offsetFromUTC))
                )
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