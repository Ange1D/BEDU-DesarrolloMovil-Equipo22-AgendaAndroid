package com.equipo22.agenda

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AdsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ads, container, false)

        val btnPaid = view.findViewById<MaterialButton>(R.id.btnPaid)
        val LinearAds = view.findViewById<LinearLayout>(R.id.LinearAds)


        loop()


        btnPaid.setOnClickListener {

            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(resources.getString(R.string.compra_dialog_title))
                .setMessage(
                    resources.getString(
                        R.string.compra_dialog
                    )
                )
                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                    Toast.makeText(requireActivity(),getString(R.string.paidOut), Toast.LENGTH_SHORT).show()
                    LinearAds.visibility=View.GONE
                }
                .setNegativeButton(resources.getString(R.string.btnCancel)) { dialog, which ->
                }
                .show()

        }
        return view
    }

    fun loop(){
        Handler(Looper.getMainLooper()).postDelayed({
            anuncios((0..2).random())
            loop()
        }, 3000)
    }
    fun anuncios(n:Int){
        val imageAds = view?.findViewById<ImageView>(R.id.imageAds)
        when(n){
            0->imageAds?.setImageResource(R.drawable.ads_becas)
            1 ->imageAds?.setImageResource(R.drawable.ads_mexico)
            2 -> imageAds?.setImageResource(R.drawable.ads_sale)
            else ->imageAds?.setImageResource(R.drawable.ads_becas)
        }
    }


}