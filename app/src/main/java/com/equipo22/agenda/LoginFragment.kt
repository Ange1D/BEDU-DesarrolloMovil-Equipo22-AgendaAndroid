package com.equipo22.agenda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {
    private lateinit var usr: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        loginButton.setOnClickListener {
            if (nameInput.text.toString().equals(usr) && (passInput.text.toString().equals(pass))){
                val intent = Intent(context, MenuActivity::class.java)
                startActivity(intent)
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

