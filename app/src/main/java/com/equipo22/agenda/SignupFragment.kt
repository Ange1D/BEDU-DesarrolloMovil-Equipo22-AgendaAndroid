package com.equipo22.agenda

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val signupButton = view.findViewById<MaterialButton>(R.id.btnSignUp2)
        val name = view.findViewById<TextInputLayout>(R.id.signUpName)
        val nameInput = view.findViewById<TextInputEditText>(R.id.layout_text_usr)
        val email = view.findViewById<TextInputLayout>(R.id.signUpEmail)
        val emailInput = view.findViewById<TextInputEditText>(R.id.layout_text_email)
        val pass1 = view.findViewById<TextInputLayout>(R.id.signUpPass)
        val passInput = view.findViewById<TextInputEditText>(R.id.layout_text_pass)
        val pass2 = view.findViewById<TextInputLayout>(R.id.signUpPass2)
        val passInput2 = view.findViewById<TextInputEditText>(R.id.layout_text_pass2)

        signupButton.isEnabled = false
        signupButton.setTextColor(Color.parseColor("#9E9E9E"))

        //para que el botón de registro sea habilitado se deben cumplir las siguientes condiciones:
        //Todos los campos deben llenarse y las contraseñas deben ser iguales
        nameInput.addTextChangedListener(object : TextWatcher {
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
                if (emailInput.text.toString().isNotEmpty() &&
                    passInput.text.toString().isNotEmpty() &&
                        passInput2.text.toString().equals(passInput.text.toString())) {
                    signupButton.isEnabled = true
                    signupButton.setTextColor(Color.parseColor("#000000"))
                }
            }
        })

        emailInput.addTextChangedListener(object : TextWatcher {
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
                if (nameInput.text.toString().isNotEmpty() &&
                    passInput.text.toString().isNotEmpty() &&
                    passInput2.text.toString().equals(passInput.text.toString())) {
                    signupButton.isEnabled = true
                    signupButton.setTextColor(Color.parseColor("#000000"))
                }
            }
        })

        passInput.addTextChangedListener(object : TextWatcher {
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
                if (nameInput.text.toString().isNotEmpty() &&
                    emailInput.text.toString().isNotEmpty() &&
                    passInput2.text.toString().equals(passInput.text.toString())) {
                    signupButton.isEnabled = true
                    signupButton.setTextColor(Color.parseColor("#000000"))
                }
            }
        })

        passInput2.addTextChangedListener(object : TextWatcher {
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
                if (nameInput.text.toString().isNotEmpty() &&
                    emailInput.text.toString().isNotEmpty() &&
                    passInput.text.toString().equals(passInput2.text.toString())) {
                    signupButton.isEnabled = true
                    signupButton.setTextColor(Color.parseColor("#000000"))
                }
            }
        })
        //Al presionar el botón se valida la dirección de correo y la longitud de la contraseña
        signupButton.setOnClickListener {

            if (!(activity as MainActivity).isValidEmail(emailInput.text.toString())) {
                email.error=getString(R.string.correoIncorrect)
            }
            else if (passInput.text!!.length < 8) {
                pass1.error=getString(R.string.passShort)
            }
            else{
                name.error=null
                email.error=null
                pass1.error=null
                pass2.error=null
                Toast.makeText(
                    context,
                    R.string.registro, Toast.LENGTH_LONG
                ).show()
                val fm: FragmentManager? = activity?.supportFragmentManager
                fm?.popBackStack()
            }
        }
        return view
    }
}