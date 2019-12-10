package com.modelo.phygital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.casos_clinicos.CasoClinico
import com.modelo.phygital.ui.casos_clinicos.CasosClinicosViewModel
import com.modelo.phygital.ui.casos_clinicos.DemoAdapterClinica



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

class DemoAdapterSesion(private val sesiones: ArrayList<Sesion>) : RecyclerView.Adapter<DemoAdapterSesion.DemoViewHolder>() {


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
                val intent = Intent(view.context,CasoClinicoTabs::class.java)

                // Also like this
                ContextCompat.startActivity(view.context, intent, null)

            }


        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoAdapterSesion.DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder_sesiones, parent, false) as View


        return DemoAdapterSesion.DemoViewHolder(view)
    }

    override fun getItemCount() = sesiones.size
    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(sesiones[position])
    }

}
private lateinit var rv: RecyclerView

class SesionesActivity : AppCompatActivity() {



    private lateinit var etPacienteSesion: EditText
    private lateinit var etSesion: EditText
    private lateinit var etFechaSesion: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etActividades: EditText


    private lateinit var btnAddButton: Button


    private var sesiones: ArrayList<Sesion> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesiones)

        etPacienteSesion = findViewById(R.id.paciente_sesion_text)
        etSesion = findViewById(R.id.sesion_numero_text)
        etFechaSesion = findViewById(R.id.fecha_sesion_text)
        etDescripcion = findViewById(R.id.descripcion_text)
        etActividades = findViewById(R.id.actividades_text)
        btnAddButton = findViewById(R.id.add_sesion_button)




        rv = findViewById<RecyclerView>(R.id.rv_sesiones).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SesionesActivity)
            adapter = DemoAdapterSesion(sesiones)
        }

        btnAddButton.setOnClickListener {

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("sesionClinica")
            val sesion = Sesion(
                etPacienteSesion.text.toString().trim(),
                etSesion.text.toString().trim(),
                etFechaSesion.text.toString().trim(),
                etDescripcion.text.toString().trim(),
                etActividades.text.toString().trim()


            )
            usersRef.push().setValue(sesion)

            etPacienteSesion.setText("")
            etSesion.setText("")
            etFechaSesion.setText("")
            etDescripcion.setText("")
            etActividades.setText("")


        }



        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("sesionClinica")
        usersRef.addChildEventListener(object : ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val sesC: Sesion? = p0.getValue(Sesion::class.java)
                sesC?.id = p0.key

                sesiones.remove(sesC!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val sesC: Sesion? = p0.getValue(Sesion::class.java)
                sesC?.id = p0.key

                sesiones.add(sesC!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val sesC: Sesion? = p0.getValue(Sesion::class.java)
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


