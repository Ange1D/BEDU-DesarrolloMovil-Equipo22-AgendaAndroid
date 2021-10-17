package com.equipo22.agenda

import android.animation.AnimatorInflater
import android.content.Intent
import android.graphics.Color.red
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
//import android.transition.TransitionManager
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
import com.equipo22.agenda.utils.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    //Esta variable ya no es necesaria
    //var usuarios: MutableList<Usuario> = ArrayList()
    private lateinit var binding: LoginFragmentBinding
    private lateinit var auth : FirebaseAuth
    companion object {
        var started: Boolean = false
        private const val TAG = "EmailPassword"
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        auth = Firebase.auth


        //usuarios.add(Usuario("usuario", "mailito@neo.com", "contraseña"))

        with (binding) {
            btnLogIn.isEnabled = false
            btnSignUp.isEnabled = false
            btnLogIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.textDisabled))
            btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.textDisabled))

            intro()
            started = true

            //Se verifica que los inputs tengan datos y sólo entonces se habilita el botón para el login
            etEmail.addTextChangedListener(object : TextWatcher {
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
                        if (etEmail.text.toString().isNotEmpty() && inputTextPass.text.toString().isNotEmpty()) {
                            btnLogIn.isEnabled = true
                            btnSignUp.isEnabled = true
                            btnLogIn.setTextColor(ContextCompat.getColor(context!!, R.color.secondaryTextColor))
                            btnSignUp.setTextColor(ContextCompat.getColor(context!!, R.color.secondaryTextColor))
                        }


                }
            })

            inputTextPass.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int,
                    count: Int, after: Int
                ) { }

                override fun onTextChanged(
                    s: CharSequence?, start: Int,
                    before: Int, count: Int
                ) {
                    if (etEmail.text.toString().isNotEmpty() && inputTextPass.text.toString().isNotEmpty()) {
                        btnLogIn.isEnabled = true
                        btnSignUp.isEnabled = true
                        btnLogIn.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.secondaryTextColor
                            )
                        )
                        btnSignUp.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.secondaryTextColor
                            )
                        )
                    }

                }

                override fun afterTextChanged(s: Editable?) { }

            })
            //--------------------------------------------------------------------------------
            //listener del botón de login
            btnLogIn.setOnClickListener {
                //TODA ESTA LÓGICA DEBE SER MODIFICADA

                val email = binding.etEmail.text.toString()
                val password = binding.inputTextPass.text.toString()

                logIn(email, password)







                /*if (etEmail.text.toString() == usuarios[0].nombre && (inputTextPass.text.toString() == usuarios[0].password)
                ) {
                    val intent = Intent(context, TareaManagementActivity::class.java)
                    
                    startActivity(intent)
                    preferences.edit()
                        .putBoolean(IS_LOGGED, true)
                        .apply()
                    requireActivity().finish()
                }
                else if (etEmail.text.toString() == usuarios[0].nombre && (inputTextPass.text.toString() != usuarios[0].password)) {
                    //Se cambia el texto de la view
                    //lostPass.text = getString(R.string.lostPass)
                    etEmail.text?.clear()
                    inputTextPass.text?.clear()
                    tilEmail.error = null
                    textPassword.error = getString(R.string.passIncorrect)

                    Toast.makeText(
                        context,
                        resources.getString(R.string.msgFailLogin), Toast.LENGTH_LONG
                    ).show()
                    //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
//                    lostPass.setOnClickListener {
//                        (activity as MainActivity).navigateTo(ResetPassFragment(), false)
//                    }
                }
                else if (etEmail.text.toString() != usuarios[0].nombre && (inputTextPass.text.toString() == usuarios[0].password)) {

                    etEmail.text?.clear()
                    inputTextPass.text?.clear()
                    tilEmail.error = getString(R.string.nameIncorrect)
                    textPassword.error = null

                    Toast.makeText(
                        context,
                        resources.getString(R.string.msgFailLogin), Toast.LENGTH_LONG
                    ).show()
                }
                else {

                    //lostPass.text = getString(R.string.lostPass)
                    etEmail.text?.clear()
                    inputTextPass.text?.clear()
                    tilEmail.error = getString(R.string.nameIncorrect)
                    textPassword.error = getString(R.string.passIncorrect)
                    //se agrega el listener a la view (solo en caso de que la contraseña no sea válida)
//                    lostPass.setOnClickListener {
//                        (activity as MainActivity).navigateTo(ResetPassFragment(), false)
//
//                    }

                }*/
            }
            //Listener del botón de signup
            btnSignUp.setOnClickListener {
                //ESTA FUNCIONALIDAD DEBE SER MODIFICADA
                val email = binding.etEmail.text.toString()
                val password = binding.inputTextPass.text.toString()

                createAccount(email, password)





                /*
                (activity as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(appLogo, appLogo.transitionName)
                    .addSharedElement(tilEmail, tilEmail.transitionName)
                    .addSharedElement(etEmail, etEmail.transitionName)
                    .addSharedElement(inputTextPass, inputTextPass.transitionName)
                    .addSharedElement(textPassword, textPassword.transitionName)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, SignupFragment())
                    .commit()
                */
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        intro()
    }

    private fun logIn(email: String, password: String) {
        //updateUI()
        //Se parece a la funcion de arriba
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Log.d(TAG, "logInSucces")
                val user = auth.currentUser
                updateUI(user,null)
                val intent = Intent(context, TareaManagementActivity::class.java)
                startActivity(intent)
                preferences.edit()
                    .putBoolean(IS_LOGGED, true)
                    .apply()
                requireActivity().finish()
            }else{
                Log.w(TAG, "failure", task.exception)
                updateUI(null,task.exception)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user, null, false)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    updateUI(null, task.exception)
                }
            }


    }


    private fun updateUI(user: FirebaseUser?, exception: Exception?, login : Boolean = true) {
        if (exception != null) {
            //binding.loading.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
            Utility.displaySnackBar(binding.root, exception.message.toString(), this.requireContext(), R.color.red)
        } else {
            val message = if(login) "Login was succesful" else "Register was successful"
            Utility.displaySnackBar(binding.root, message, this.requireContext(), R.color.green)
            //binding.loading.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
        }
    }




    private fun intro () {
        with (binding) {
            if (!started) {
                AnimatorInflater.loadAnimator(context, R.animator.intro_up).apply {
                    setTarget(appLogo)
                    start()
                }
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(btnsNative)
                start()
            }

            AnimatorInflater.loadAnimator(context, R.animator.intro_down).apply {
                setTarget(btnGoogle)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(etEmail)
                start()
            }
            AnimatorInflater.loadAnimator(context, R.animator.intro_appear).apply {
                setTarget(tilEmail)
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
}

