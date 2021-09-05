package com.equipo22.agenda

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.equipo22.agenda.MainActivity.Companion.IS_LOGGED
import com.equipo22.agenda.MainActivity.Companion.preferences
import com.equipo22.agenda.databinding.LoginFragmentBinding
import com.equipo22.agenda.tareas.TareaManagementActivity


class LoginFragment : Fragment() {
    var usuarios: MutableList<Usuario> = ArrayList()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        usuarios.add(Usuario("usuario", "mailito@neo.com", "contraseña"))

        binding.btnLogIn.isEnabled = false
        binding.btnLogIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.textDisabled))

        intro()

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
                with (binding) {
                    if (inputTextUsr.text.toString().isNotEmpty()) {
                        btnLogIn.isEnabled = true
                        btnLogIn.setTextColor(ContextCompat.getColor(context!!, R.color.secondaryTextColor))
                    }
                }

            }
        })

        binding.inputTextPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int,
                count: Int, after: Int
            ) { }

            override fun onTextChanged(
                s: CharSequence?, start: Int,
                before: Int, count: Int
            ) {
                with (binding) {
                    btnLogIn.isEnabled = true
                    btnLogIn.setTextColor(ContextCompat.getColor(context!!, R.color.secondaryTextColor))
                }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        //--------------------------------------------------------------------------------
        //listener del botón de login
        binding.btnLogIn.setOnClickListener {
            if (binding.inputTextUsr.text.toString().equals(usuarios[0].nombre) && (binding.inputTextPass.text.toString()
                    .equals(usuarios[0].password))
            ) {
                val intent = Intent(context, TareaManagementActivity::class.java)
                startActivity(intent)
                preferences.edit()
                    .putBoolean(IS_LOGGED, true)
                    .apply()
                requireActivity().finish()
            }
            else if (binding.inputTextUsr.text.toString().equals(usuarios[0].nombre) && (!binding.inputTextPass.text.toString().equals(usuarios[0].password))) {
                //Se cambia el texto de la view
                    with (binding) {
                        lostPass.text = getString(R.string.lostPass)
                        inputTextUsr.text?.clear()
                        inputTextPass.text?.clear()
                        personName.error = null
                        textPassword.error = getString(R.string.passIncorrect)
                    }
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
                with (binding){
                    inputTextUsr.text?.clear()
                    inputTextPass.text?.clear()
                    personName.error = getString(R.string.nameIncorrect)
                    textPassword.error = null
                }
                Toast.makeText(
                    context,
                    resources.getString(R.string.msgFailLogin), Toast.LENGTH_LONG
                ).show()
            }
            else {
                with (binding) {
                    lostPass.text = getString(R.string.lostPass)
                    inputTextUsr.text?.clear()
                    inputTextPass.text?.clear()
                    personName.error = getString(R.string.nameIncorrect)
                    textPassword.error = getString(R.string.passIncorrect)
                    //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
                    lostPass.setOnClickListener {
                        (activity as MainActivity).navigateTo(ResetPassFragment(), false)

                    }
                }
            }
        }
        //Listener del botón de signup
        binding.btnSignUp.setOnClickListener {
            //(activity as MainActivity).navigateTo(ResetPassFragment(), false)
            (activity as MainActivity).navigateTo(SignupFragment(), false)
        }
        return view
    }

    private fun intro () {
        with (binding) {
            AnimatorInflater.loadAnimator(context, R.animator.intro_up).apply {
                setTarget(appLogo)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(btnLogIn)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(lostPass)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(btnSignUp)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(inputTextUsr)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(personName)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(inputTextPass)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(textPassword)
                start()
            }
        }

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logo = binding.appLogo
        ViewCompat.setTransitionName(logo, "logo")

    }*/

}

