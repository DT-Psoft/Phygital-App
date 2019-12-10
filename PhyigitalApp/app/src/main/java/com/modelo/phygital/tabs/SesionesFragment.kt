package com.modelo.phygital.tabs


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.modelo.phygital.CasoClinicoTabs
import com.modelo.phygital.R
import com.modelo.phygital.Sesion
import com.modelo.phygital.SesionesActivity
import com.modelo.phygital.ui.casos_clinicos.CasoClinico
import com.modelo.phygital.ui.casos_clinicos.DemoAdapterClinica

import kotlinx.android.synthetic.main.fragment_sesiones_contenido.*


data class ConSesion(var paciente: String = "", var sesion: String = "", var fecha: String = "", var descripcion: String = "", var actividades: String ="") {
    var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConSesion

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}

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

        public fun bind(cses: ConSesion) {

            etTitulo1.setText("${cses.paciente},${cses.sesion}")
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


        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(cses[position])
    }

    override fun getItemCount() = cses.size
}
private lateinit var rv: RecyclerView


class SesionesFragment : Fragment() {

    private var cses: ArrayList<ConSesion> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_sesiones_contenido, container, false)





        rv = root.findViewById(R.id.rv_sesiones_contenido) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapterConSesion(cses)




        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("sesionClinica")
        usersRef.addChildEventListener(object : ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val csesC: ConSesion? = p0.getValue(ConSesion::class.java)
                csesC?.id = p0.key

                cses.remove(csesC!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val csesC: ConSesion? = p0.getValue(ConSesion::class.java)
                csesC?.id = p0.key

                cses.add(csesC!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val csesC: ConSesion? = p0.getValue(ConSesion::class.java)
                csesC?.id = p0.key
                val currentCaso: ConSesion? = cses.get(cses.indexOf(csesC))
                currentCaso?.paciente = csesC!!.paciente
                currentCaso?.sesion = csesC!!.sesion
                currentCaso?.fecha = csesC!!.fecha
                currentCaso?.descripcion = csesC!!.descripcion
                currentCaso?.actividades = csesC!!.actividades

                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return root
    }

}





