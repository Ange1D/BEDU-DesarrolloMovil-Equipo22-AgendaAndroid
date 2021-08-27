package com.equipo22.agenda.tareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.equipo22.agenda.ConfiguracionFragment
import com.equipo22.agenda.R
import com.equipo22.agenda.Tarea
import com.equipo22.agenda.databinding.ActivityTareaManagementBinding
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
        val binding = ActivityTareaManagementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setTareas()

        if (savedInstanceState == null) {
            title = getString(R.string.titlePerfil)
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
            R.id.action_add -> {
                title = getString(R.string.titleNew)
                navigateTo(AgregarTareaFragment(), false)}
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
            R.id.action_edit -> {
                title = getString(R.string.titleEdit)
                navigateTo(EditarTareaFragment(), false)}
            R.id.action_conf -> {
                title = getString(R.string.titleSettings)
                navigateTo(ConfiguracionFragment(), false)}
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
        if (SHOWING_FRAGMENT == "AgregarTarea" || SHOWING_FRAGMENT == "Detalles"
            || SHOWING_FRAGMENT == "Configuracion") {
            title = getString(R.string.titlePerfil)
            navigateTo(VerListadoFragment(), false)
            tareasMenu.findItem(R.id.action_add).isVisible = true
            tareasMenu.findItem(R.id.action_edit).isVisible = false
            tareasMenu.findItem(R.id.action_delete).isVisible = false
            tareasMenu.findItem(R.id.action_conf).isVisible = true
        } else if (SHOWING_FRAGMENT == "EditarTarea") {
            title = getString(R.string.titleDetalles)
            navigateTo(DetallesTareaFragment(), false)
            tareasMenu.findItem(R.id.action_add).isVisible = false
            tareasMenu.findItem(R.id.action_edit).isVisible = true
            tareasMenu.findItem(R.id.action_delete).isVisible = true
            tareasMenu.findItem(R.id.action_conf).isVisible = false
        }  else {
            super.onBackPressed()
        }
    }
}