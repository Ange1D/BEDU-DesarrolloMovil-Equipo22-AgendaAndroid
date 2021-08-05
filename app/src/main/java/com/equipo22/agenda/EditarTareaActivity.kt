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

    var tareaprevia = listOf("Tarea 1","Tarea 2")
    var frecuencia:Array<String> = arrayOf("Una sola vez","Todos los Lunes","Todos los Martes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tarea)

        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        val spinner:AutoCompleteTextView = findViewById(R.id.tareaPrevia)
        val spinner2: AutoCompleteTextView = findViewById(R.id.frecuencia)
        txtTitle = findViewById(R.id.titulo)
        txtHour = findViewById(R.id.descripcion)
        val title = intent.getStringExtra("Titulo")
        val hour = intent.getStringExtra("Horario")

        if (title != null && hour != null) {
            txtTitle.setText(title)
            txtHour.setText(hour)
        }

        val previaAdapter = ArrayAdapter(this, R.layout.dropdown_item, tareaprevia)
        spinner.setAdapter(previaAdapter)

        val frecuenciaAdapter = ArrayAdapter(this, R.layout.dropdown_item, frecuencia)
        spinner2.setAdapter(previaAdapter)

        btnSave.setOnClickListener {
            val intent = Intent(this, VerTareasActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, VerTareasActivity::class.java)
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