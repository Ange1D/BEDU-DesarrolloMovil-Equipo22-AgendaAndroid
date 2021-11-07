package com.equipo22.agenda.tareas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.equipo22.agenda.LoginActivity
import com.equipo22.agenda.LoginActivity.Companion.IS_LOGGED
import com.equipo22.agenda.LoginActivity.Companion.preferences
import com.equipo22.agenda.R
import com.equipo22.agenda.PrincipalActivity
import com.equipo22.agenda.databinding.FragmentConfiguracionBinding
import com.equipo22.agenda.utils.getNumberOfTareas
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

class ConfiguracionFragment : Fragment() {

    val CHANNEL_RECORDATORIOS = "RECORDATORIOS"

    companion object {
        private const val TAG = "GoogleAuth"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentConfiguracionBinding.inflate(layoutInflater)
        val view = binding.root

        PrincipalActivity.SHOWING_FRAGMENT = "Configuracion"
        PrincipalActivity.tareasMenu.findItem(R.id.add_dest).isVisible = true
        PrincipalActivity.tareasMenu.findItem(R.id.edit_dest).isVisible = false
        PrincipalActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false

        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d(TAG, "User exist successful")
            // User is signed in
        } else {
            Log.d(TAG, "User not logged")
            // No user is signed in
        }

        binding.btnsaveSettings.setOnClickListener{

            if(isValidEmail(binding.inputEmail.text.toString())){
                binding.layoutEmail.error = null
                Toast.makeText(activity, R.string.change,Toast.LENGTH_SHORT).show()
            }else{
                binding.layoutEmail.error = getString(R.string.errorEmail)
            }

        }

        binding.btnLogOut.setOnClickListener {
            preferences.edit()
                .putBoolean(IS_LOGGED, false)
                .apply()

            Log.d(TAG, user!!.providerId)
            Firebase.auth.signOut()

            val logOut = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(logOut)
            requireActivity().finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        binding.btnTry.setOnClickListener {
            touchNotification()
        }
        binding.btnTryCrash.visibility=View.GONE
        binding.btnTryCrash.setOnClickListener {

            try {
                throw RuntimeException("Prueba Crash")
            } catch (ex: NullPointerException) {
                FirebaseCrashlytics.getInstance().setUserId("Bedu-PruebaCrash")
                FirebaseCrashlytics.getInstance().log("Prueba Provocado")
                FirebaseCrashlytics.getInstance().recordException(ex)
            }

        }

        return  view
    }

    private fun touchNotification(){
        //Un PendingIntent para dirigirnos a una actividad pulsando la notificación
        val intent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val msg=" "+getString(R.string.notification_body)
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_RECORDATORIOS)
            .setSmallIcon(R.drawable.chronomaster01)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getNumberOfTareas(VerListadoFragment.tareas).toString() + msg )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(30, builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val name = getString(R.string.channel_recordatorios)
        val descriptionText = getString(R.string.recordatorios_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_RECORDATORIOS, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
