package com.equipo22.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.equipo22.agenda.databinding.FragmentConfiguracionBinding
import com.equipo22.agenda.tareas.TareaManagementActivity


class ConfiguracionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentConfiguracionBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "Configuracion"
        TareaManagementActivity.tareasMenu.findItem(R.id.action_add).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_edit).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_delete).isVisible = false
        TareaManagementActivity.tareasMenu.findItem(R.id.action_conf).isVisible = false


        binding.btnsaveSettings.setOnClickListener{

            if (!(!binding.editOld.text!!.isEmpty() && binding.editOld.text!!.length >= 8)){
                binding.oldPass.error = getString(R.string.errorPass)}else{
                binding.oldPass.error = null
            }
            if (binding.editNewPass.text!!.isEmpty() || binding.editNewPass.text!!.length<8){
                binding.newPass.error = getString(R.string.errorPass)}else{
                binding.newPass.error = null
            }

            if( binding.editOld.text!!.length >= 8 && binding.editNewPass.text!!.length >= 8 ){
                binding.newPass.error = null
                binding.oldPass.error = null
                Toast.makeText(activity,"Datos guardados",Toast.LENGTH_SHORT).show()
            }

        }

        return  view
    }


}