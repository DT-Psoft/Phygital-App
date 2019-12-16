package com.modelo.phygital.DemoAdapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.CasoClinicoTabs
import com.modelo.phygital.R
import com.modelo.phygital.ui.Sesion

class DemoAdapterAddSesion(private val sesiones: ArrayList<Sesion>) : RecyclerView.Adapter<DemoAdapterAddSesion.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var tvPacienteSesion: TextView
        private var tvSesion: TextView
        private var tvFechaSesion: TextView
        // private var tvDescripcion: TextView
        private var btnDeleteSesion: ImageButton
        private var btnEditSesion: ImageButton



        init {
            tvPacienteSesion = view.findViewById(R.id.paciente_sesion_tv)
            tvSesion = view.findViewById(R.id.sesion_tv)
            tvFechaSesion = view.findViewById(R.id.fecha_tv)
            // tvDescripcion= view.findViewById(R.id.descripcion_tv)
            btnDeleteSesion = view.findViewById(R.id.delete_sesion_button)
            btnEditSesion = view.findViewById(R.id.edit_sesion_button)
        }

        public fun bind(sesion: Sesion) {

            tvPacienteSesion.setText("${sesion.paciente}")
            tvSesion.setText("${sesion.sesion}")
            tvFechaSesion.setText("${sesion.fecha}")
            //  tvDescripcion.setText("${sesion.descripcion}")
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("sesionClinica")

            btnDeleteSesion.setOnClickListener{

                usersRef.child(sesion.id!!).removeValue()


            }
            btnEditSesion.setOnClickListener{
                val intent = Intent(view.context,
                    CasoClinicoTabs::class.java)

                // Also like this
                ContextCompat.startActivity(view.context, intent, null)

            }


        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder_sesiones, parent, false) as View


        return DemoViewHolder(
            view
        )
    }

    override fun getItemCount() = sesiones.size
    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(sesiones[position])
    }

}