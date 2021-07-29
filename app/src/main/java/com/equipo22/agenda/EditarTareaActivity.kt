package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class EditarTareaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var txtTitle: EditText
    private lateinit var txtHour: EditText

    var tareaprevia:Array<String> = arrayOf("Tarea 1","Tarea 2")
    var frecuencia:Array<String> = arrayOf("Una sola vez","Todos los Lunes","Todos los Martes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tarea)

        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        val spinner: Spinner = findViewById(R.id.tareaPrevia)
        val spinner2: Spinner = findViewById(R.id.frecuencia)
        txtTitle = findViewById(R.id.titulo)
        txtHour = findViewById(R.id.descripcion)
        val title = intent.getStringExtra("Titulo")
        val hour = intent.getStringExtra("Horario")

        if (title != null && hour != null) {
            txtTitle.setText(title)
            txtHour.setText(hour)
        }

        ArrayAdapter(this, android.R.layout.simple_spinner_item, tareaprevia)
            .also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext ,tareaprevia[position] , Toast.LENGTH_LONG).show()
            }
        }

        ArrayAdapter(this, android.R.layout.simple_spinner_item, frecuencia)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter
            }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext ,frecuencia[position] , Toast.LENGTH_LONG).show()
            }
        }



        btnSave.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
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
        showDialog("No seleccionaste idioma","Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna")
        Toast.makeText(applicationContext, "No idioma" , Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun showDialog(title:String,message:String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK"){dialogInterface, which -> }
            .create()
            .show()
    }

}