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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ResetPassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.reset_pass_fragment, container, false)

        val btnSend = view.findViewById<MaterialButton>(R.id.btnSend)
        val txtEmailSnd = view.findViewById<TextInputEditText>(R.id.txtEmail_input)

        btnSend.isEnabled = false
        //Se agrega un Listener al input para habilitar el botón una vez que se ha ingresado una dirección de email
        txtEmailSnd.addTextChangedListener(object : TextWatcher {
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
            }
        })
        //Se muestra un toast al presionar el botón enviar
        btnSend.setOnClickListener {
            Toast.makeText(
                context,
                "La liga para restablecer la contraseña ha sido enviada", Toast.LENGTH_LONG
            ).show()
            val fm: FragmentManager? = activity?.supportFragmentManager
            fm?.popBackStack()
        }
        return view
    }


}