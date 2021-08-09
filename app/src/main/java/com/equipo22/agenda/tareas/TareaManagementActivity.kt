package com.equipo22.agenda.tareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.equipo22.agenda.R
import com.equipo22.agenda.Tarea
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TareaManagementActivity : AppCompatActivity() {
    companion object {
        lateinit var SHOWING_FRAGMENT: String
        val tareas = mutableListOf<Tarea>()
        val titulosTareas = mutableListOf("Ninguna")
        val frecuencia = mutableListOf(
            "Diaria",
            "Semanal",
            "Mensual",
            "Anual",
            "Todos los Domingos",
            "Todos los Lunes",
            "Todos los Martes",
            "Todos los Miércoles",
            "Todos los Jueves",
            "Todos los Viernes",
            "Todos los Sábados",
        )
        val prioridad = mutableListOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
        )
        lateinit var tareaSeleccionada: Tarea
        var tareaSeleccionadaIndex = 0
        lateinit var tareasMenu: Menu
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

    fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
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
        tareasMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> navigateTo(AgregarTareaFragment(), false)
            R.id.action_delete -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.delete_dialog_title))
                    .setMessage(
                        resources.getString(
                            R.string.delete_dialog_description,
                            tareaSeleccionada.titulo
                        )
                    )
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                        onBackPressed()
                        tareas.removeAt(tareaSeleccionadaIndex)
                        titulosTareas.removeAt(tareaSeleccionadaIndex + 1)
                    }
                    .setNegativeButton(resources.getString(R.string.btnCancel)) { dialog, which ->
                    }
                    .show()
            }
            R.id.action_edit -> navigateTo(EditarTareaFragment(), false)
        }
        return super.onOptionsItemSelected(item)
    }

    fun setTareas(): MutableList<Tarea> {
        tareas.add(
            Tarea(
                "Desayunar",
                "07/Agosto/2021",
                "09:00",
                "Hora de desayunar.",
                "Ninguna",
                "Diaria",
                10,
                "Finalizada"
            )
        )
        titulosTareas.add("Desayunar")
        tareas.add(
            Tarea(
                "Comer",
                "07/Agosto/2021",
                "15:00",
                "Hora de comer.",
                "Ninguna",
                "Diaria",
                10,
                "Pendiente"
            )
        )
        titulosTareas.add("Comer")
        tareas.add(
            Tarea(
                "Cenar",
                "07/Agosto/2021",
                "20:00",
                "Hora de cenar.",
                "Ninguna",
                "Diaria",
                10,
                "Pendiente"
            )
        )
        titulosTareas.add("Cenar")

        return tareas
    }

    override fun onBackPressed() {
        if (SHOWING_FRAGMENT == "AgregarTarea" || SHOWING_FRAGMENT == "Detalles") {
            navigateTo(VerListadoFragment(), false)
            tareasMenu.findItem(R.id.action_add).isVisible = true
            tareasMenu.findItem(R.id.action_edit).isVisible = false
            tareasMenu.findItem(R.id.action_delete).isVisible = false
        } else if (SHOWING_FRAGMENT == "EditarTarea") {
            navigateTo(DetallesTareaFragment(), false)
            tareasMenu.findItem(R.id.action_add).isVisible = false
            tareasMenu.findItem(R.id.action_edit).isVisible = true
            tareasMenu.findItem(R.id.action_delete).isVisible = true
        } else {
            super.onBackPressed()
        }
    }
}