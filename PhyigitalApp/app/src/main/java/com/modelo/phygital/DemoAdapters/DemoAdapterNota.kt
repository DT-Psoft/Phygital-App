package com.modelo.phygital.DemoAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.R
import com.modelo.phygital.tabs.Nota

class DemoAdapterNota(private val notas: ArrayList<Nota>) : RecyclerView.Adapter<DemoAdapterNota.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var etTitulo: TextView
        private lateinit var etDescripcion: TextView
        private var btnDelete: ImageButton



        init {
            etTitulo = view.findViewById(R.id.titulo_nota_tv)
            etDescripcion= view.findViewById(R.id.descripcion_nota_tv)
            btnDelete = view.findViewById(R.id.delete_nota_button)

        }

        public fun bind(notas: Nota) {


            etTitulo.setText("${notas.titulo}")
            etDescripcion.setText("${notas.descripcion}")

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("nota")

            btnDelete.setOnClickListener{

                usersRef.child(notas.id!!).removeValue()
                val snack = Snackbar.make(it,"Nota eliminada con exito", Snackbar.LENGTH_LONG)

            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder_notas, parent, false) as View


        return DemoViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(notas[position])
    }

    override fun getItemCount() = notas.size
}