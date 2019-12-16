package com.modelo.phygital.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.AddActivities.AddSesion
import com.modelo.phygital.DemoAdapters.DemoAdapterAddSesion
import com.modelo.phygital.R


data class Sesion(var paciente: String = "", var sesion: String = "", var fecha: String = "", var descripcion: String = "", var actividades: String ="") {
    var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sesion

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}


private lateinit var rv: RecyclerView

class SesionesActivity : AppCompatActivity() {


    private lateinit var btnAddButton: Button


    private var sesiones: ArrayList<Sesion> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesiones)


        btnAddButton = findViewById(R.id.add_button)




        rv = findViewById<RecyclerView>(R.id.rv_sesiones).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SesionesActivity)
            adapter =
                DemoAdapterAddSesion(sesiones)
        }

        btnAddButton.setOnClickListener {


            val intent = Intent(this, AddSesion::class.java)
            startActivity(intent)
            finish()

        }



        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("sesionClinica")
        usersRef.addChildEventListener(object : ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val sesC: Sesion? = p0.getValue(
                    Sesion::class.java)
                sesC?.id = p0.key

                sesiones.remove(sesC!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val sesC: Sesion? = p0.getValue(
                    Sesion::class.java)
                sesC?.id = p0.key

                sesiones.add(sesC!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val sesC: Sesion? = p0.getValue(
                    Sesion::class.java)
                sesC?.id = p0.key
                val currentSesion: Sesion? = sesiones.get(sesiones.indexOf(sesC))
                currentSesion?.paciente = sesC!!.paciente
                currentSesion?.sesion = sesC!!.sesion
                currentSesion?.fecha = sesC!!.fecha
                currentSesion?.descripcion = sesC!!.descripcion
                currentSesion?.actividades = sesC!!.actividades

                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

}


