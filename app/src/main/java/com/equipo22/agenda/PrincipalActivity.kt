package com.equipo22.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.equipo22.agenda.databinding.ActivityPrincipalBinding
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.room.TareaDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.util.concurrent.Executors

class PrincipalActivity : AppCompatActivity() {
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
        lateinit var tareasMenu: Menu
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.tarea_management_menu, menu)
        tareasMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
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
                        TareaDB.getInstance(this)?.tareaDAO()?.removeVehicleById(
                            tareaSeleccionada.id)
                        Handler(Looper.getMainLooper()).post {
                            onBackPressed()
                        }
                    }
                }
                .setNegativeButton(resources.getString(R.string.btnCancel)) { dialog, which ->
                }
                .show()
            return false
        } else {
            return try {
                (item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                        || super.onOptionsItemSelected(item))
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().log("Error No Identificado")
                FirebaseCrashlytics.getInstance().recordException(e)

                FirebaseCrashlytics.getInstance().setUserId("Bedu-Crash-NoIdentificado")

                false
            }
        }
    }

    override fun onBackPressed() {
        if (SHOWING_FRAGMENT == "AgregarTarea" || SHOWING_FRAGMENT == "Detalles"
            || SHOWING_FRAGMENT == "Configuracion" || SHOWING_FRAGMENT == "EditarTarea") {
            tareasMenu.findItem(R.id.add_dest).isVisible = true
            tareasMenu.findItem(R.id.edit_dest).isVisible = false
            tareasMenu.findItem(R.id.action_delete).isVisible = false
        }

        super.onBackPressed()
    }
}