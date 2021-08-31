package com.equipo22.agenda.tareas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo22.agenda.*
import com.equipo22.agenda.databinding.FragmentVerListadoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random
import kotlin.random.nextInt

class VerListadoFragment : Fragment() {
    private lateinit var binding: FragmentVerListadoBinding
    private val baseURL = "https://api-thirukkural.vercel.app/"

    private val horizontalLinearLayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private lateinit var mAdapter: TareasRecyclerAdapter
    private var listener: (Tarea) -> Unit = {
        TareaManagementActivity.tareaSeleccionada = it
        TareaManagementActivity.tareaSeleccionadaIndex = TareaManagementActivity.tareas.indexOf(it)
        requireActivity().title = getString(R.string.titleDetalles)
        (activity as TareaManagementActivity).navigateTo(DetallesTareaFragment(), false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerListadoBinding.inflate(layoutInflater)
        val view = binding.root

        TareaManagementActivity.SHOWING_FRAGMENT = "VerListado"

        mAdapter =
            TareasRecyclerAdapter(requireActivity(), TareaManagementActivity.tareas, listener)

        binding.recyclerTareas.layoutManager = horizontalLinearLayoutManager
        binding.recyclerTareas.adapter = mAdapter

        loadPoem()

        return view
    }

    private fun loadPoem() {
        val poemNumber = Random.nextInt(1..1330)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val endpoint = retrofit.create(PoemServices::class.java)
        val call = endpoint.getNumber(poemNumber.toString())

        call.enqueue(object : Callback<Poem> {
            override fun onResponse(call: Call<Poem>, response: Response<Poem>) {
                val quote = response.body()

                binding.txtQuoteOfTheDay.text = "${quote?.eng}"
            }

            override fun onFailure(call: Call<Poem>, t: Throwable) {
                Log.e("error", "Error: $t")
            }

        })
    }
}