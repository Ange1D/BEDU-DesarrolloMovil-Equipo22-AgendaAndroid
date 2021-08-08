package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.equipo22.agenda.tareas.TareaManagementActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var btnShow: Button
    private lateinit var btnConfig: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnShow = findViewById(R.id.btnShow)
        btnConfig = findViewById(R.id.btnConfig)
        
        btnShow.setOnClickListener {
            val intent = Intent(this, TareaManagementActivity::class.java)
            startActivity(intent)
        }

        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
        }
    }
}
