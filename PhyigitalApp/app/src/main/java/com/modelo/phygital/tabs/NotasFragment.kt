package com.modelo.phygital.tabs


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.AddActivities.AddNotas
import com.modelo.phygital.CasoClinicoTabs
import com.modelo.phygital.DemoAdapters.DemoAdapterNota
import com.modelo.phygital.MenuPrincipal
import com.modelo.phygital.R


data class Nota(var titulo: String = "", var descripcion: String = "") {
    var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Nota

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}

private lateinit var rv: RecyclerView

class NotasFragment : Fragment() {

    private var notas: ArrayList<Nota> = arrayListOf()
    private lateinit var btnAddButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_notas, container, false)

        btnAddButton = root.findViewById(R.id.add_button)

        rv = root.findViewById(R.id.rv_notas) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapterNota(notas)

        btnAddButton.setOnClickListener{

            val intent = Intent(activity, AddNotas::class.java)
            startActivity(intent)

        }



        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("nota")
        usersRef.addChildEventListener(object : ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val notasC: Nota? = p0.getValue(Nota::class.java)
                notasC?.id = p0.key

                notas.remove(notasC!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val notasC: Nota? = p0.getValue(Nota::class.java)
                notasC?.id = p0.key

                notas.add(notasC!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val notasC: Nota? = p0.getValue(Nota::class.java)
                notasC?.id = p0.key
                val currentCaso: Nota? = notas.get(notas.indexOf(notasC))
                currentCaso?.titulo = notasC!!.titulo
                currentCaso?.descripcion = notasC!!.descripcion

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
