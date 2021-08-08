package com.equipo22.agenda.tareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.Tarea

class TareaManagementActivity : AppCompatActivity() {
    companion object {
        val tareas = mutableListOf<Tarea>()
        lateinit var tareaSeleccionada: Tarea
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea_management)

        setTareas()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, VerListadoFragment())
                .commit()
        }
    }

    public fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.tarea_management_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun setTareas(): MutableList<Tarea> {
        tareas.add(Tarea("Desayunar", "07/Agosto/2021", "09:00", "Hora de desayunar.", "Ninguna", "Diaria", 10))
        tareas.add(Tarea("Comer", "07/Agosto/2021", "15:00", "Hora de comer.", "Ninguna", "Diaria", 10))
        tareas.add(Tarea("Cenar", "07/Agosto/2021", "20:00", "Hora de cenar.", "Ninguna", "Diaria", 10))

        return tareas
    }
}