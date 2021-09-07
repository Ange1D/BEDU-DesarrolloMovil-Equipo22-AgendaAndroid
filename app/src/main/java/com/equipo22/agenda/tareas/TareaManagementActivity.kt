package com.equipo22.agenda.tareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.equipo22.agenda.ConfiguracionFragment
import com.equipo22.agenda.R
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.databinding.ActivityTareaManagementBinding
import com.equipo22.agenda.room.TareaDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.Executors

class TareaManagementActivity : AppCompatActivity() {
    companion object {
        lateinit var SHOWING_FRAGMENT: String
        val titulosTareas = mutableListOf("Ninguna")
        val frecuencia = mutableListOf(
            "Nunca",
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

        if (savedInstanceState == null) {
            title = getString(R.string.titlePerfil)
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.intro_down, R.animator.nada, R.animator.nada, R.animator.exit_up)
                .commit()
        }
    }

    fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.animator.intro_down, R.animator.nada, R.animator.nada, R.animator.exit_up)
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
                navigateTo(AgregarTareaFragment(), false)
            }
            R.id.action_conf -> {
                title = getString(R.string.titleSettings)
                navigateTo(ConfiguracionFragment(), false)
            }
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
                        Executors.newSingleThreadExecutor().execute {
                            TareaDB.getInstance(this)?.tareaDAO()?.removeVehicleById(tareaSeleccionada.id)
                            Handler(Looper.getMainLooper()).post {
                                onBackPressed()
                            }
                        }
                    }
                    .setNegativeButton(resources.getString(R.string.btnCancel)) { dialog, which ->
                    }
                    .show()
            }
            R.id.action_edit -> {
                navigateTo(EditarTareaFragment(), false)
            }
        }
        return super.onOptionsItemSelected(item)
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