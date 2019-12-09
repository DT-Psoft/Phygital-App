package com.modelo.phygital.ui.casos_clinicos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.CasoClinicoTabs
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
                val snack = Snackbar.make(it,"Caso clinico eliminado con exito",Snackbar.LENGTH_LONG)
                snack.show()

            }
            btnEdit.setOnClickListener{
                val snack = Snackbar.make(it,"Esto sera para editar, aun no se como saldra",Snackbar.LENGTH_LONG)
                snack.show()
                val intent = Intent(view.context,CasoClinicoTabs::class.java)

                // Also like this
                startActivity(view.context,intent,null)

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


        etPinPaciente = root.findViewById(R.id.pin_paciente_text)
        etPacientePadencia= root.findViewById(R.id.padencia_inp_text)
        btnAddButton = root.findViewById(R.id.add_caso_button)

        rv = root.findViewById(R.id.rv_casos_clinicos) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapterClinica(casos)

        btnAddButton.setOnClickListener {

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("casosClinicos")
            val user = CasoClinico(
                etPinPaciente.text.toString().trim(),
                etPacientePadencia.text.toString().trim()

            )
            usersRef.push().setValue(user)

            etPinPaciente.setText("")
            etPacientePadencia.setText("")

        }


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





