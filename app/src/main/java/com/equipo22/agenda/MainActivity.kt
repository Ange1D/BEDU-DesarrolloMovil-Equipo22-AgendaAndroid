package com.equipo22.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.text.Editable
//import android.text.TextWatcher
import android.widget.Button
//import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

//import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //Función que agrega el contenedor a la activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }

    //Función para navegar entre los fragmentos
    fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}