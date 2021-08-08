package com.equipo22.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

       val signupButton = view.findViewById<MaterialButton>(R.id.btnSignUp)
//
       signupButton.setOnClickListener {
            (activity as MainActivity).navigateTo(SignupFragment(), false)
       }

        return view
    }
}

