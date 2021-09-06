package com.equipo22.agenda

import android.animation.AnimatorInflater
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater
import com.equipo22.agenda.databinding.SignupFragmentBinding

class SignupFragment : Fragment() {

    private lateinit var binding: SignupFragmentBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Se infla la activity con el fragmento de signup
        binding = SignupFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        //Transition inflater de androidx
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(R.transition.change_image_trans)

        intro()

        //Se inicializa el estado del botón
        with (binding) {
            btnSignUp.isEnabled = false
            btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.textDisabled))

            //para que el botón de registro sea habilitado se deben cumplir las siguientes condiciones:
            //Todos los campos deben llenarse y las contraseñas deben ser iguales
            inputTextUsr.addTextChangedListener(object : TextWatcher {
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
                    if (inputEmail?.text.toString().isNotEmpty() &&
                        inputTextPass.text.toString().isNotEmpty() &&
                        layoutTextPass2.text.toString() == inputTextPass.text.toString()
                    ) {
                        btnSignUp.isEnabled = true
                        btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryTextColor))
                    }
                }
            })

            inputEmail?.addTextChangedListener(object : TextWatcher {
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
                    if (inputTextUsr.text.toString().isNotEmpty() &&
                        inputTextPass.text.toString().isNotEmpty() &&
                        layoutTextPass2.text.toString() == inputTextPass.text.toString()
                    ) {
                        btnSignUp.isEnabled = true
                        btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryTextColor))
                    }
                }
            })

            inputTextPass.addTextChangedListener(object : TextWatcher {
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
                    if (inputTextUsr.text.toString().isNotEmpty() &&
                        inputEmail?.text.toString().isNotEmpty() &&
                        layoutTextPass2.text.toString() == inputTextPass.text.toString()
                    ) {
                        btnSignUp.isEnabled = true
                        btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryTextColor))
                    }
                }
            })

            layoutTextPass2.addTextChangedListener(object : TextWatcher {
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
                    if (inputTextUsr.text.toString().isNotEmpty() &&
                        inputEmail?.text.toString().isNotEmpty() &&
                        inputTextPass.text.toString() == layoutTextPass2.text.toString()
                    ) {
                        btnSignUp.isEnabled = true
                        btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryTextColor))
                    }
                }
            })
            //-----------------------------------------------------------------------------------------
            //Al presionar el botón se valida la dirección de correo y la longitud de la contraseña
            btnSignUp.setOnClickListener {
                //Se valida la nomenclatura del email
                if (!(activity as MainActivity).isValidEmail(inputEmail?.text.toString())) {
                    layoutEmail?.error = getString(R.string.correoIncorrect)
                }
                //Se valida que el password tenga al menos 8 caracteres de largo
                else if (inputTextPass.text!!.length < 8) {
                    textPassword.error = getString(R.string.passShort)
                }
                //Si los datos son correctos, se muestra el Toast con el mensaje
                else{
                    personName.error = null
                    layoutEmail?.error = null
                    textPassword.error = null
                    signUpPass2.error = null
                    Toast.makeText(
                        context,
                        R.string.registro, Toast.LENGTH_LONG
                    ).show()
                    val fm: FragmentManager? = activity?.supportFragmentManager
                    fm?.popBackStack()
                }
            }
        }

        return view
    }

    private fun intro () {
        with(binding) {
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(inputEmail)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(layoutEmail)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(signUpPass2)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(layoutTextPass2)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(personName)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(textPassword)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(btnSignUp)
                start()
            }
        }
    }
}