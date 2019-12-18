package com.modelo.phygital.DemoAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.R
import com.modelo.phygital.tabs.ConSesion

class DemoAdapterConSesion(private val cses: ArrayList<ConSesion>) : RecyclerView.Adapter<DemoAdapterConSesion.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var etTitulo1: TextView
        private lateinit var etTitulo2: TextView
        private lateinit var etActividades: TextView
        private lateinit var etDescripcion: TextView



        init {
            etTitulo1 = view.findViewById(R.id.titulo_sesion_contenido_tv)
            etTitulo2= view.findViewById(R.id.titulo_secundario_sesion_tv)
            etDescripcion= view.findViewById(R.id.descripcion_sesion_tv)
            etActividades = view.findViewById(R.id.contenido_sesion_tv)




        }

        public fun bind(cses: ConSesion, position: Int) {

            etTitulo1.setText("${"Sesion" + " " + position}, ${cses.paciente}")
            etTitulo2.setText("${cses.fecha}")
            etDescripcion.setText("${cses.descripcion}")
            etActividades.setText("${cses.actividades}")
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("sesionClinica")

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder_sesiones_contenido, parent, false) as View


        return DemoViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(cses[position], position+1)
    }

    override fun getItemCount() = cses.size
}