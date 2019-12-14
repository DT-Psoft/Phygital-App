package com.modelo.phygital

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.casos_clinicos.CasoClinico

class DemoAdapterClinica(private val casos: ArrayList<CasoClinico>) : RecyclerView.Adapter<DemoAdapterClinica.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var tvPinPaciente: TextView
        private var tvPadenciaPaciente: TextView
        private var btnDelete: ImageButton
        private var btnEdit: ImageButton

        init {
            tvPinPaciente = view.findViewById(R.id.paciente_pin)
            tvPadenciaPaciente = view.findViewById(R.id.padencia_paciente)
            btnDelete = view.findViewById(R.id.delete_caso_button)
            btnEdit = view.findViewById(R.id.edit_caso_button)

        }

        public fun bind(caso: CasoClinico) {

            tvPinPaciente.setText("${caso.pin}")
            tvPadenciaPaciente.setText("${caso.padencia}")
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("casosClinicos")

            btnDelete.setOnClickListener{

                usersRef.child(caso.id!!).removeValue()
                val snack = Snackbar.make(it,"Caso clinico eliminado con exito", Snackbar.LENGTH_LONG)
                snack.show()

            }
            btnEdit.setOnClickListener{
                val intent = Intent(view.context,SesionesActivity::class.java)

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
            .inflate(R.layout.rv_demo_holder_casos_clinicos, parent, false) as View


        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(casos[position])
    }

    override fun getItemCount() = casos.size
}