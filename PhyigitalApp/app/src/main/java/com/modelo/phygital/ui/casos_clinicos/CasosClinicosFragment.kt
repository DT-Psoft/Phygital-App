package com.modelo.phygital.ui.casos_clinicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.DemoAdapters.DemoAdapterClinica
import com.modelo.phygital.R


data class CasoClinico(var pin: String = "", var padencia: String = "") {
    var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CasoClinico

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}


private lateinit var rv: RecyclerView


class CasosClinicosFragment : Fragment() {

    private lateinit var casosClinicosViewModel: CasosClinicosViewModel
    private lateinit var layoutname: LinearLayout

    private lateinit var etPinPaciente: EditText
    private lateinit var etPacientePadencia: EditText

    private lateinit var btnAddButton: Button


    private var casos: ArrayList<CasoClinico> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_casos_clinicos, container, false)



        rv = root.findViewById(R.id.rv_casos_clinicos) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapterClinica(casos)



        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("casosClinicos")
        usersRef.addChildEventListener(object : ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val casoC: CasoClinico? = p0.getValue(CasoClinico::class.java)
                casoC?.id = p0.key

                casos.remove(casoC!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val casoC: CasoClinico? = p0.getValue(CasoClinico::class.java)
                casoC?.id = p0.key

                casos.add(casoC!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val casoC: CasoClinico? = p0.getValue(CasoClinico::class.java)
                casoC?.id = p0.key
                val currentCaso: CasoClinico? = casos.get(casos.indexOf(casoC))
                currentCaso?.pin = casoC!!.pin
                currentCaso?.padencia = casoC!!.padencia

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





