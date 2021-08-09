package com.equipo22.agenda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.equipo22.agenda.tareas.TareaManagementActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var usr: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var usuarios: List<Usuario>
//        usuarios.add
//        usuarios[0].nombre = "usuario"
//        usuarios[0].password = "contraseña"
        usr = "usuario"
        pass = "contraseña"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val signupButton = view.findViewById<MaterialButton>(R.id.btnSignUp)
        val txtLstPass = view.findViewById<TextView>(R.id.lostPass)
        val loginButton = view.findViewById<MaterialButton>(R.id.btnLogIn)
        val nameInput = view.findViewById<TextInputEditText>(R.id.input_text_usr)
        val passInput = view.findViewById<TextInputEditText>(R.id.input_text_pass)
        val name = view.findViewById<TextInputLayout>(R.id.personName)
        val password = view.findViewById<TextInputLayout>(R.id.textPassword)

        loginButton.setOnClickListener {
            if (nameInput.text.toString().equals(usr) && (passInput.text.toString().equals(pass))){
                name.error=null
                password.error=null
                val intent = Intent(context, TareaManagementActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else if(nameInput.text.toString().equals(usr) && (!passInput.text.toString().equals(pass))){
                name.error=null
                password.error=getString(R.string.passIncorrect)
            }else if(!nameInput.text.toString().equals(usr) && (passInput.text.toString().equals(pass))){
                name.error=getString(R.string.nameIncorrect)
                password.error=null
        }else{
                name.error=getString(R.string.nameIncorrect)
                password.error=getString(R.string.passIncorrect)
            }
        }

        signupButton.setOnClickListener {
            (activity as MainActivity).navigateTo(SignupFragment(), false)
        }

        txtLstPass.setOnClickListener {
            (activity as MainActivity).navigateTo(ResetPassFragment(), false)
        }

        return view
    }
}

