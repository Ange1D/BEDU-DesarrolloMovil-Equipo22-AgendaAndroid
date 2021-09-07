package com.equipo22.agenda

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import com.equipo22.agenda.databinding.ActivityMainBinding
import com.equipo22.agenda.tareas.TareaManagementActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "com.equipo22.agenda.sharedpreferences"
        const val IS_LOGGED = "IS_LOGGED"
        lateinit var preferences: SharedPreferences
    }

    private lateinit var binding: ActivityMainBinding

    //Función que agrega el contenedor a la activity
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ChronoMasterTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

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
            .setCustomAnimations(R.animator.intro_down, R.animator.nada, R.animator.nada, R.animator.exit_up)
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
    //Función para validar que las direcciones de correo electrónico tengan la nomenclatura correcta
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onStart() {
        if (preferences.getBoolean(IS_LOGGED, false)) {
            val isLogged = Intent(this, TareaManagementActivity::class.java)
            startActivity(isLogged)
            this.finish()
        }
        super.onStart()
    }
}