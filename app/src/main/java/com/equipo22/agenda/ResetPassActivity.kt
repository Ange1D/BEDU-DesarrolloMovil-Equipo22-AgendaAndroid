package com.equipo22.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResetPassActivity : AppCompatActivity() {
    private lateinit var btnSend: Button
    private lateinit var txtEmailSnd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        btnSend = findViewById(R.id.btnSend)
        txtEmailSnd = findViewById(R.id.txtEmail)

        btnSend.isEnabled = false

        txtEmailSnd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                btnSend.isEnabled = true
            }
        })

        btnSend.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "La liga para restablecer la contraseña ha sido enviada", Toast.LENGTH_LONG).show()
        }
    }
}