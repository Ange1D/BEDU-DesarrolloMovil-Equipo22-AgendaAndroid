package com.equipo22.agenda

import android.animation.AnimatorInflater
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.equipo22.agenda.databinding.ActivityLoginBinding
import com.equipo22.agenda.utils.Utility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "com.equipo22.agenda.shared-preferences"
        const val IS_LOGGED = "IS_LOGGED"
        const val TAG = "EmailPassword"
        lateinit var preferences: SharedPreferences
        private const val RC_SIGN_IN = 9001
    }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    var started: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Instaciación de Firebase y Auth
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //End Configure Google

        setTheme(R.style.ChronoMasterTheme)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (preferences.getBoolean(IS_LOGGED, false)) {
            val isLogged = Intent(this, PrincipalActivity::class.java)
            startActivity(isLogged)
            this.finish()
        }

        with(binding) {
            btnLogIn.isEnabled = false
            btnSignUp.isEnabled = false
            btnLogIn.setTextColor(getColor(R.color.textDisabled))
            btnSignUp.setTextColor(getColor(R.color.textDisabled))

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
                    if (etEmail.text.toString().isNotEmpty() && inputTextPass.text.toString()
                            .isNotEmpty()
                    ) {
                        btnLogIn.isEnabled = true
                        btnSignUp.isEnabled = true
                        btnLogIn.setTextColor(
                            getColor(R.color.secondaryTextColor)
                        )
                        btnSignUp.setTextColor(
                            getColor(R.color.secondaryTextColor)
                        )
                    }
                }
            })

            inputTextPass.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?, start: Int,
                    before: Int, count: Int
                ) {
                    if (etEmail.text.toString().isNotEmpty() && inputTextPass.text.toString()
                            .isNotEmpty()
                    ) {
                        btnLogIn.isEnabled = true
                        btnSignUp.isEnabled = true
                        btnLogIn.setTextColor(
                            getColor(R.color.secondaryTextColor)
                        )
                        btnSignUp.setTextColor(
                            getColor(R.color.secondaryTextColor)
                        )
                    }

                }

                override fun afterTextChanged(s: Editable?) {}
            })
            //--------------------------------------------------------------------------------
            btnGoogle.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
            //listener del botón de login
            btnLogIn.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.inputTextPass.text.toString()

                logIn(email, password)
            }
            //Listener del botón de signup
            btnSignUp.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.inputTextPass.text.toString()

                createAccount(email, password)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        intro()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d(TAG,currentUser.toString())
        if (currentUser!=null)
            preferences.edit()
                .putBoolean(IS_LOGGED, true)
                .apply()
        updateUI(currentUser, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user,null)
                    startNextAct()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null, task.exception)
                }
            }
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "logInSucces")
                    val user = auth.currentUser
                    updateUI(user, null)
                    startNextAct()
                } else {
                    Log.w(TAG, "failure", task.exception)
                    updateUI(null, task.exception)
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

    private fun updateUI(user: FirebaseUser?, exception: Exception?, login: Boolean = true) {
        Log.d(TAG, user?.email.toString())

        if(user?.email!=null) {
            if (exception != null) {
                binding.btnLogIn.visibility = View.VISIBLE
                Utility.displaySnackBar(
                    binding.root,
                    exception.message.toString(),
                    this,
                    R.color.red
                )
            } else {
                val message = if (login) "Login was succesful" else "Register was successful"
                Utility.displaySnackBar(binding.root, message, this, R.color.green)
                binding.btnLogIn.visibility = View.VISIBLE
            }
        }
    }

    private fun startNextAct(){
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
        preferences.edit()
            .putBoolean(IS_LOGGED, true)
            .apply()
        this.finish()
    }


    private fun intro() {
        with(binding) {
            if (!started) {
                AnimatorInflater.loadAnimator(baseContext, R.animator.intro_up).apply {
                    setTarget(appLogo)
                    start()
                }
            }
            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_down).apply {
                setTarget(btnsNative)
                start()
            }

            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_down).apply {
                setTarget(btnGoogle)
                start()
            }
            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_appear).apply {
                setTarget(etEmail)
                start()
            }
            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_appear).apply {
                setTarget(tilEmail)
                start()
            }
            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_appear).apply {
                setTarget(inputTextPass)
                start()
            }
            AnimatorInflater.loadAnimator(baseContext, R.animator.intro_appear).apply {
                setTarget(textPassword)
                start()
            }
        }
    }
}