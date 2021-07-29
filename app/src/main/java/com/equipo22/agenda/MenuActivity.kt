package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    private lateinit var btnShow: Button
    private lateinit var btnAdd: Button
    private lateinit var btnConfig: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnShow = findViewById(R.id.btnShow)
        btnAdd = findViewById(R.id.btnAdd)
        btnConfig = findViewById(R.id.btnConfig)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AgregarTareaActivity::class.java)
            startActivity(intent)
        }

        
        btnShow.setOnClickListener {
            val intent = Intent(this, VerTareasActivity::class.java)
            startActivity(intent)
        }

        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
        }
    }
}
