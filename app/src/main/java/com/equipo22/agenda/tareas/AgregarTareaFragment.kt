package com.equipo22.agenda.tareas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.equipo22.agenda.CameraActivity
import com.equipo22.agenda.R
import com.equipo22.agenda.PrincipalActivity
import com.equipo22.agenda.databinding.FragmentAgregarTareaBinding
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.room.TareaDB
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
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
        PrincipalActivity.SHOWING_FRAGMENT = "AgregarTarea"
        PrincipalActivity.tareasMenu.findItem(R.id.add_dest).isVisible = false
        PrincipalActivity.tareasMenu.findItem(R.id.edit_dest).isVisible = false
        PrincipalActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
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

        binding.txtTareaPrevia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, PrincipalActivity.titulosTareas))
        binding.txtFrecuencia.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, PrincipalActivity.frecuencia))
        binding.txtPrioridad.setAdapter(ArrayAdapter(requireActivity(), R.layout.dropdown_item, PrincipalActivity.prioridad))
        binding.txtFecha.setOnClickListener {
            datePicker.show((activity as PrincipalActivity).supportFragmentManager, resources.getString(R.string.fecha))
        }
        binding.txtHora.setOnClickListener {
            timePicker.show((activity as PrincipalActivity).supportFragmentManager, resources.getString(R.string.hora))
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
                        (activity as PrincipalActivity).onBackPressed()
                    }
                }
            } else {
                MaterialAlertDialogBuilder((activity as PrincipalActivity))
                    .setTitle(resources.getString(R.string.incomplete_data_title))
                    .setMessage(resources.getString(R.string.incomplete_data_description))
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->

                    }
                    .show()
            }
        }
        binding.btnCancel.setOnClickListener {
            (activity as PrincipalActivity).onBackPressed()
        }
        binding.btnOpenCamera.visibility=View.GONE
        binding.btnOpenCamera.setOnClickListener{
            openCamera()
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

    private fun openCamera(){
        val intent = Intent(activity, CameraActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
