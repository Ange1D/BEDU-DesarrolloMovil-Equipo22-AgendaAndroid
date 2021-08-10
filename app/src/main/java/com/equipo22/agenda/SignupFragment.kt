package com.equipo22.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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

        signupButton.setOnClickListener{

            if(nameInput.text.toString().isEmpty()){
                name.error=getString(R.string.nameIncorrect)
            }else if(!isValidEmail(emailInput.text.toString())){
                email.error=getString(R.string.correoIncorrect)
            }else if(passInput.text!!.length<8){
                pass1.error=getString(R.string.passShort)
            } else if(!passInput.text.toString().equals(passInput2.text.toString())){
                pass2.error=getString(R.string.passCompare)
            }else{
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

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}