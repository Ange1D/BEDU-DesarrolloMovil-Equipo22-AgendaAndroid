package com.equipo22.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo22.agenda.databinding.FragmentAdsBinding




class AdsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAdsBinding.inflate(layoutInflater)
        val view = binding.root
        return view

    }
}