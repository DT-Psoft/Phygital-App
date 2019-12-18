package com.modelo.phygital.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.modelo.phygital.DemoAdapters.DemoAdapterConSesion
import com.modelo.phygital.R


data class ConSesion(var paciente: String = "", var fecha: String = "", var descripcion: String = "", var actividades: String ="") {
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





