package com.equipo22.agenda

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.equipo22.agenda.tareas.TareaManagementActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    var usuarios: MutableList<Usuario> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        usuarios.add(Usuario("usuario", "mailito@neo.com", "contraseña"))

        val signupButton = view.findViewById<MaterialButton>(R.id.btnSignUp)
        val txtLstPass = view.findViewById<TextView>(R.id.lostPass)
        val loginButton = view.findViewById<MaterialButton>(R.id.btnLogIn)
        val nameInput = view.findViewById<TextInputEditText>(R.id.input_text_usr)
        val passInput = view.findViewById<TextInputEditText>(R.id.input_text_pass)
        val name = view.findViewById<TextInputLayout>(R.id.personName)
        val password = view.findViewById<TextInputLayout>(R.id.textPassword)

        loginButton.isEnabled = false
        loginButton.setTextColor(resources.getColor(R.color.textDisabled))
        txtLstPass.text = "O"

        //Se verifica que los inputs tengan datos y sólo entonces se habilita el botón para el login
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
                if (passInput.text.toString().isNotEmpty()) {
                    loginButton.isEnabled = true
                    loginButton.setTextColor(resources.getColor(R.color.secondaryTextColor))
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
                if (nameInput.text.toString().isNotEmpty()) {
                    loginButton.isEnabled = true
                    loginButton.setTextColor(resources.getColor(R.color.secondaryTextColor))
                }
            }
        })
        //--------------------------------------------------------------------------------
        //listener del botón de login
        loginButton.setOnClickListener {
            if (nameInput.text.toString().equals(usuarios[0].nombre) && (passInput.text.toString()
                    .equals(usuarios[0].password))
            ) {
                val intent = Intent(context, TareaManagementActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else if (nameInput.text.toString().equals(usuarios[0].nombre) && (!passInput.text.toString().equals(usuarios[0].password))) {
                //Se cambia el texto de la view
                txtLstPass.text = "¿Olvidaste la contraseña?"
                nameInput.text?.clear()
                passInput.text?.clear()
                name.error = null
                password.error = getString(R.string.passIncorrect)
                Toast.makeText(
                    context,
                    "Usuario o contraseña de inicio de sesión incorrectos.", Toast.LENGTH_LONG
                ).show()
                //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
                txtLstPass.setOnClickListener {
                    (activity as MainActivity).navigateTo(ResetPassFragment(), false)
                }
            }
            else if (!nameInput.text.toString().equals(usuarios[0].nombre) && (passInput.text.toString().equals(usuarios[0].password))) {
                nameInput.text?.clear()
                passInput.text?.clear()
                name.error = getString(R.string.nameIncorrect)
                password.error = null
                Toast.makeText(
                    context,
                    "Usuario o contraseña de inicio de sesión incorrectos.", Toast.LENGTH_LONG
                ).show()
            }
            else {
                txtLstPass.text = "¿Olvidaste la contraseña?"
                nameInput.text?.clear()
                passInput.text?.clear()
                name.error = getString(R.string.nameIncorrect)
                password.error = getString(R.string.passIncorrect)
                //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
                txtLstPass.setOnClickListener {
                    (activity as MainActivity).navigateTo(ResetPassFragment(), false)
                }
            }
        }
        //Listener del botón de signup
        signupButton.setOnClickListener {
            (activity as MainActivity).navigateTo(SignupFragment(), false)
        }
        return view
    }
}

