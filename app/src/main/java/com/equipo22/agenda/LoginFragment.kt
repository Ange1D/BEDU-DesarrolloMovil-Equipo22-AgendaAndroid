package com.equipo22.agenda

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.equipo22.agenda.databinding.LoginFragmentBinding
import com.equipo22.agenda.tareas.TareaManagementActivity


class LoginFragment : Fragment() {
    var usuarios: MutableList<Usuario> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = LoginFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        //val view = inflater.inflate(R.layout.login_fragment, container, false)

        usuarios.add(Usuario("usuario", "mailito@neo.com", "contraseña"))

        binding.btnLogIn.isEnabled = false
        binding.btnLogIn.setTextColor(resources.getColor(R.color.textDisabled))
        binding.lostPass.text = "O"

        //Se verifica que los inputs tengan datos y sólo entonces se habilita el botón para el login
        binding.inputTextUsr.addTextChangedListener(object : TextWatcher {
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
                if (binding.inputTextPass.text.toString().isNotEmpty()) {
                    binding.btnLogIn.isEnabled = true
                    binding.btnLogIn.setTextColor(resources.getColor(R.color.secondaryTextColor))
                }
            }
        })

        binding.inputTextPass.addTextChangedListener(object : TextWatcher {
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
                if (binding.inputTextUsr.text.toString().isNotEmpty()) {
                    binding.btnLogIn.isEnabled = true
                    binding.btnLogIn.setTextColor(resources.getColor(R.color.secondaryTextColor))
                }
            }
        })
        //--------------------------------------------------------------------------------
        //listener del botón de login
        binding.btnLogIn.setOnClickListener {
            if (binding.inputTextUsr.text.toString().equals(usuarios[0].nombre) && (binding.inputTextPass.text.toString()
                    .equals(usuarios[0].password))
            ) {
                val intent = Intent(context, TareaManagementActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else if (binding.inputTextUsr.text.toString().equals(usuarios[0].nombre) && (!binding.inputTextPass.text.toString().equals(usuarios[0].password))) {
                //Se cambia el texto de la view
                binding.lostPass.text = resources.getString(R.string.lostPass)
                binding.inputTextUsr.text?.clear()
                binding.inputTextPass.text?.clear()
                binding.personName.error = null
                binding.textPassword.error = getString(R.string.passIncorrect)
                Toast.makeText(
                    context,
                    resources.getString(R.string.msgFailLogin), Toast.LENGTH_LONG
                ).show()
                //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
                binding.lostPass.setOnClickListener {
                    (activity as MainActivity).navigateTo(ResetPassFragment(), false)
                }
            }
            else if (!binding.inputTextUsr.text.toString().equals(usuarios[0].nombre) && (binding.inputTextPass.text.toString().equals(usuarios[0].password))) {
                binding.inputTextUsr.text?.clear()
                binding.inputTextPass.text?.clear()
                binding.personName.error = getString(R.string.nameIncorrect)
                binding.textPassword.error = null
                Toast.makeText(
                    context,
                    resources.getString(R.string.msgFailLogin), Toast.LENGTH_LONG
                ).show()
            }
            else {
                binding.lostPass.text = resources.getString(R.string.lostPass)
                binding.inputTextUsr.text?.clear()
                binding.inputTextPass.text?.clear()
                binding.personName.error = getString(R.string.nameIncorrect)
                binding.textPassword.error = getString(R.string.passIncorrect)
                //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
                binding.lostPass.setOnClickListener {
                    (activity as MainActivity).navigateTo(ResetPassFragment(), false)
                }
            }
        }
        //Listener del botón de signup
        binding.btnSignUp.setOnClickListener {
            (activity as MainActivity).navigateTo(SignupFragment(), false)
        }
        return view
    }
}

