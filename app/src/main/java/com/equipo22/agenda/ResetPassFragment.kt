package com.equipo22.agenda


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.equipo22.agenda.databinding.ResetPassFragmentBinding


class ResetPassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ResetPassFragmentBinding.inflate(layoutInflater)
        val view = binding.root



        binding.btnSend.isEnabled = false
        binding.btnSend.setTextColor(resources.getColor(R.color.textDisabled))
        //Se agrega un Listener al input para habilitar el botón una vez que se ha ingresado una dirección de email
        binding.txtEmailInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                binding.btnSend.isEnabled = true
                binding.btnSend.setTextColor(resources.getColor(R.color.secondaryTextColor))
            }
        })
        //Se muestra un toast al presionar el botón enviar
        binding.btnSend.setOnClickListener {
            if((activity as MainActivity).isValidEmail(binding.txtEmailInput.text.toString())){
                Toast.makeText(
                    context,
                    R.string.reset, Toast.LENGTH_LONG
                ).show()
                val fm: FragmentManager? = activity?.supportFragmentManager
                fm?.popBackStack()
            }else{
                binding.txtEmail.error=getString(R.string.correoIncorrect) }
            }

        return view
    }

}