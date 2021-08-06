package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class EditarTareaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var txtTitulo: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtFechaInicio: TextInputEditText
    private lateinit var txtHoraInicio: TextInputEditText
    private lateinit var txtFechaFin: TextInputEditText
    private lateinit var txtHoraFin: TextInputEditText

    var tareaprevia = listOf("Tarea 1", "Tarea 2")
    var frecuencia = listOf("Una sola vez", "Todos los Lunes", "Todos los Martes")
    var prioridad = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tarea)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.fecha))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            when {
                txtFechaInicio.hasFocus() -> {
                    txtFechaInicio.setText(
                        datePicker.headerText
                            .replace(" ", "/")
                    )
                }
                txtFechaFin.hasFocus() -> {
                    txtFechaFin.setText(
                        datePicker.headerText
                            .replace(" ", "/")
                    )
                }
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
                txtHoraInicio.hasFocus() -> {
                    txtHoraInicio.setText("${timePicker.hour}:${timePicker.minute} hrs.")
                }
                txtHoraFin.hasFocus() -> {
                    txtHoraFin.setText("${timePicker.hour}:${timePicker.minute} hrs.")
                }
            }
        }

        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        txtFechaInicio = findViewById(R.id.fechaInicio)
        txtHoraInicio = findViewById(R.id.horaInicio)
        txtFechaFin = findViewById(R.id.fechaFin)
        txtHoraFin = findViewById(R.id.horaFin)

        val spinner: AutoCompleteTextView = findViewById(R.id.tareaPrevia)
        val spinner2: AutoCompleteTextView = findViewById(R.id.frecuencia)
        val spinner3: AutoCompleteTextView = findViewById(R.id.prioridad)
        txtTitulo = findViewById(R.id.titulo)
        txtDescripcion = findViewById(R.id.descripcion)
        val title = intent.getStringExtra("Titulo")
        val hour = intent.getStringExtra("Horario")

        if (title != null && hour != null) {
            txtTitulo.setText(title)
            txtDescripcion.setText(hour)
        }

        val previaAdapter = ArrayAdapter(this, R.layout.dropdown_item, tareaprevia)
        spinner.setAdapter(previaAdapter)

        val frecuenciaAdapter = ArrayAdapter(this, R.layout.dropdown_item, frecuencia)
        spinner2.setAdapter(frecuenciaAdapter)

        val prioridadAdapter = ArrayAdapter(this, R.layout.dropdown_item, prioridad)
        spinner3.setAdapter(prioridadAdapter)

        btnSave.setOnClickListener {
            val intent = Intent(this, VerTareasActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, VerTareasActivity::class.java)
            startActivity(intent)
        }

        txtFechaInicio.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                datePicker.show(supportFragmentManager, "Fecha")
        }
        txtFechaInicio.setOnClickListener {
            datePicker.show(supportFragmentManager, "Fecha")
        }
        txtFechaFin.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                datePicker.show(supportFragmentManager, "Fecha")
        }
        txtFechaInicio.setOnClickListener {
            datePicker.show(supportFragmentManager, "Fecha")
        }
        txtHoraInicio.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                timePicker.show(supportFragmentManager, "Fecha")
        }
        txtHoraInicio.setOnClickListener {
            timePicker.show(supportFragmentManager, "Fecha")
        }
        txtHoraFin.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                timePicker.show(supportFragmentManager, "Fecha")
        }
        txtHoraInicio.setOnClickListener {
            timePicker.show(supportFragmentManager, "Fecha")
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.rb_pendiente ->
                    if (checked) {

                    }
                R.id.rb_finalizado ->
                    if (checked) {

                    }
            }
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        showDialog(
            "No seleccionaste idioma",
            "Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna"
        )
        Toast.makeText(applicationContext, "No idioma", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> }
            .create()
            .show()
    }
}