package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class AgregarTareaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var btnSave: Button


    var tareaprevia:Array<String> = arrayOf("Tarea 1","Tarea 2")
    var frecuencia:Array<String> = arrayOf("Una sola vez","Todos los Lunes","Todos los Martes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_tarea)

        btnSave = findViewById(R.id.btnSave)

        val spinner: Spinner = findViewById(R.id.tareaPrevia)
        val spinner2: Spinner = findViewById(R.id.frecuencia)

        ArrayAdapter(this, android.R.layout.simple_spinner_item, tareaprevia)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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