package com.equipo22.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.equipo22.agenda.tareas.TareaManagementActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ConfiguracionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_configuracion, container, false)

        TareaManagementActivity.SHOWING_FRAGMENT = "Configuracion"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false

        val oldPassword = view.findViewById<TextInputLayout>(R.id.oldPass)
        val editOldPassword = view.findViewById<TextInputEditText>(R.id.editOld)
        val newPassword = view.findViewById<TextInputLayout>(R.id.newPass)
        val editNewPassword = view.findViewById<TextInputEditText>(R.id.editNewPass)
        val save = view.findViewById<MaterialButton>(R.id.btnsaveSettings)

        save.setOnClickListener{

            if (!(!editOldPassword.text!!.isEmpty() && editOldPassword.text!!.length >= 8)){
                oldPassword.error = getString(R.string.errorPass)}else{
                oldPassword.error = null
            }
            if (editNewPassword.text!!.isEmpty() || editNewPassword.text!!.length<8){
                newPassword.error = getString(R.string.errorPass)}else{
                newPassword.error = null
            }

            if( editOldPassword.text!!.length >= 8 && editNewPassword.text!!.length >= 8 ){
                newPassword.error = null
                oldPassword.error = null
                Toast.makeText(activity,"Datos guardados",Toast.LENGTH_SHORT).show()
            }

        }

        return  view
    }


}