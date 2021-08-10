package com.equipo22.agenda

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ResetPassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.reset_pass_fragment, container, false)

        val email = view.findViewById<TextInputLayout>(R.id.txtEmail)
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
            if(isValidEmail(txtEmailSnd.text.toString())){
                Toast.makeText(
                    context,
                    R.string.reset, Toast.LENGTH_LONG
                ).show()
                val fm: FragmentManager? = activity?.supportFragmentManager
                fm?.popBackStack()
            }else{
                email.error=getString(R.string.correoIncorrect) }
            }

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}