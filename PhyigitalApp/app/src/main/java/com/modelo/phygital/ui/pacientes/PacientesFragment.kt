package com.modelo.phygital.ui.pacientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modelo.phygital.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.DemoAdapterPaciente


data class User(var firstName: String = "", var lastName: String = "", var age: Int = 0, var state: String = "") {
    var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}


private lateinit var rv: RecyclerView



class HomeFragment : Fragment() {

    private lateinit var pacientesViewModel: PacientesViewModel
    private lateinit var layoutname: LinearLayout



    private var users : ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pacientesViewModel =
            ViewModelProviders.of(this).get(PacientesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pacientes, container, false)



       // val ly = view!!.findViewById(R.id.ly) as LinearLayout

        //layoutname = root2.findViewById(R.id.layout_name)

        rv = root.findViewById(R.id.rv_paciente) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapterPaciente(users)





        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("app").child("users")
        usersRef.addChildEventListener(object: ChildEventListener {

            override fun onChildRemoved(p0: DataSnapshot) {
                val user : User? = p0.getValue(User::class.java)
                user?.id = p0.key

                users.remove(user!!)
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val user : User? = p0.getValue(User::class.java)
                user?.id = p0.key

                users.add(user!!)
                rv.adapter?.notifyDataSetChanged()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val user : User? = p0.getValue(User::class.java)
                user?.id = p0.key
                val currentUser : User? = users.get(users.indexOf(user))
                currentUser?.firstName = user!!.firstName
                currentUser?.lastName = user!!.lastName
                currentUser?.age = user!!.age
     //           currentUser?.state = user!!.state
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