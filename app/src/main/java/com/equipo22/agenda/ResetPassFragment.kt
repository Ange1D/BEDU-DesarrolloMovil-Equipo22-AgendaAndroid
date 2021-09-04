package com.equipo22.agenda

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.equipo22.agenda.databinding.ResetPassFragmentBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ResetPassFragment : Fragment() {

    private lateinit var binding: ResetPassFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResetPassFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        with (binding) {
            btnSend.isEnabled = false
            btnSend.setTextColor(ContextCompat.getColor(requireContext(), R.color.textDisabled))
            //Se agrega un Listener al input para habilitar el botón una vez que se ha ingresado una dirección de email
            txtEmailInput.addTextChangedListener(object : TextWatcher {
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
                    btnSend.isEnabled = true
                    btnSend.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryTextColor))
                }
            })
            //Se muestra un toast al presionar el botón enviar
            btnSend.setOnClickListener {
                if((activity as MainActivity).isValidEmail(txtEmailInput.text.toString())){
                    Toast.makeText(
                        context,
                        R.string.reset, Toast.LENGTH_LONG
                    ).show()
                    val fm: FragmentManager? = activity?.supportFragmentManager
                    fm?.popBackStack()
                }else{
                    txtEmail.error=getString(R.string.correoIncorrect) }
            }

        }

        return view
    }

}