package com.equipo22.agenda.tareas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo22.agenda.*
import com.equipo22.agenda.apiServices.Poem
import com.equipo22.agenda.apiServices.PoemServices
import com.equipo22.agenda.databinding.FragmentVerListadoBinding
import com.equipo22.agenda.room.Tarea
import com.equipo22.agenda.room.TareaDB
import com.equipo22.agenda.room.TareasRecyclerAdapter
import com.equipo22.agenda.PrincipalActivity.Companion.titulosTareas
import com.equipo22.agenda.utils.finishedTareasPercentage
import com.equipo22.agenda.utils.getNumberOfTareas
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        var tareas = mutableListOf<Tarea>()
    }

    val binding by lazy { FragmentVerListadoBinding.inflate(layoutInflater) }
    private val baseURL = "https://api-thirukkural.vercel.app/"
    lateinit var mAdapter: TareasRecyclerAdapter
    private val horizontalLinearLayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private var listener: (Tarea) -> Unit = {
        PrincipalActivity.tareaSeleccionada = it
        requireActivity().title = getString(R.string.titleDetalles)
        findNavController().navigate(VerListadoFragmentDirections.actionVerListadoFragmentToDetallesTareaFragment(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = binding.root

        PrincipalActivity.SHOWING_FRAGMENT = "VerListado"

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

        try {
            loadPoem()
        }catch (e: Exception){
            FirebaseCrashlytics.getInstance().log("Error al cargar poema")
            FirebaseCrashlytics.getInstance().recordException(e)

            FirebaseCrashlytics.getInstance().setUserId("Bedu-Crash-Poem")
            FirebaseCrashlytics.getInstance().setCustomKey("TareasTotales", getNumberOfTareas(tareas))
        }
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