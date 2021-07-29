package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.text.Editable
//import android.text.TextWatcher
import android.widget.Button
//import android.widget.EditText
import android.widget.TextView
//import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogIn: Button
    private lateinit var btnSingUp: Button
    //private lateinit var txtUsr: EditText
    //private lateinit var txtPass: EditText
    private lateinit var txtLstPass: TextView
    //private lateinit var usr: String
    //private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogIn = findViewById(R.id.btnLogIn)
        btnSingUp = findViewById(R.id.btnSignUp)
        txtLstPass = findViewById(R.id.lostPass)

        txtLstPass.setOnClickListener {
            val intent = Intent(this, ResetPassActivity::class.java)
            startActivity(intent)
        }

        btnSingUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}