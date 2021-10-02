package com.equipo22.agenda.tareas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo22.agenda.*
import com.equipo22.agenda.databinding.FragmentVerListadoBinding
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.room.TareaDB
import com.equipo22.agenda.tareas.TareaManagementActivity.Companion.titulosTareas
import com.equipo22.agenda.utils.finishedTareasPercentage
import com.equipo22.agenda.utils.getNumberOfTareas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import kotlin.random.Random
import kotlin.random.nextInt

class VerListadoFragment : Fragment() {
    companion object {
        lateinit var tareas: MutableList<Tarea>

    }

    private lateinit var binding: FragmentVerListadoBinding
    private val baseURL = "https://api-thirukkural.vercel.app/"
    lateinit var mAdapter: TareasRecyclerAdapter
    private val horizontalLinearLayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private var listener: (Tarea) -> Unit = {
        TareaManagementActivity.tareaSeleccionada = it
        TareaManagementActivity.tareaSeleccionadaIndex = it.id
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

        Executors.newSingleThreadExecutor().execute {
            tareas = TareaDB.getInstance(requireContext())
                ?.tareaDAO()
                ?.getAllTareas() as MutableList<Tarea>
            for (tarea in tareas) {
                titulosTareas.add(tarea.titulo)
            }
        }

        Handler(Looper.getMainLooper()).post {
            mAdapter = TareasRecyclerAdapter(requireActivity(), tareas, listener)
            binding.recyclerTareas.layoutManager = horizontalLinearLayoutManager
            binding.recyclerTareas.adapter = mAdapter
        }

        loadPoem()
        binding.txtTotalTareas.text = getString(R.string.totalTateas, getNumberOfTareas(tareas).toString())
        binding.txtFinishedTareas.text = getString(R.string.finishedTareas, finishedTareasPercentage(tareas).toString())

        return view
    }

    private fun loadPoem() {
        val poemNumber = Random.nextInt(1..1330)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val endpoint = retrofit.create(PoemServices::class.java)
        val call = endpoint.getPoem(poemNumber)

        call.enqueue(object : Callback<Poem> {
            override fun onResponse(call: Call<Poem>, response: Response<Poem>) {
                val quote = response.body()
                Log.e("Respuesta","${response.body().toString()}")

                binding.txtQuoteOfTheDay.text = "${quote?.eng}"
            }

            override fun onFailure(call: Call<Poem>, t: Throwable) {
                Log.e("error", "Error: $t")
            }

        })
    }

    override fun onResume() {
        titulosTareas.clear()
        titulosTareas.add("Ninguna")
        for (tarea in tareas) {
            titulosTareas.add(tarea.titulo)
        }
        binding.txtTotalTareas.text = getString(R.string.totalTateas, getNumberOfTareas(tareas).toString())
        binding.txtFinishedTareas.text = getString(R.string.finishedTareas, finishedTareasPercentage(tareas).toString())
        super.onResume()
    }
}