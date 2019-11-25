package com.example.registrofisio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrofisio.R
import kotlinx.android.synthetic.main.rv_demo_holder.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase


data class User(var firstName: String = "", var lastName: String = "", var age: Int = 0) {
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

class DemoAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var tvFullName: TextView
        private var tvAge: TextView


        init {
            tvFullName = view.findViewById(R.id.full_name)
            tvAge = view.findViewById(R.id.age)



        }

        public fun bind(user: User) {
            tvFullName.setText("${user.lastName}, ${user.firstName}")
            tvAge.setText("${user.age}")

        }



    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder, parent, false) as View

        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}
private lateinit var rv: RecyclerView



class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var layoutname: LinearLayout

    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etAge : EditText
    private lateinit var btnAddButton : Button

    private var users : ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        etFirstName = root.findViewById(R.id.frame_text)
        etLastName = root.findViewById(R.id.lname_text)
        etAge = root.findViewById(R.id.age_text)
        btnAddButton = root.findViewById(R.id.add_button)
       // val ly = view!!.findViewById(R.id.ly) as LinearLayout

       // layoutname = root2.findViewById(R.id.layout_name)

        rv = root.findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = DemoAdapter(users)

        btnAddButton.setOnClickListener{

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("users")
            val user = User(
                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etAge.text.toString().toInt()
            )
           usersRef.push().setValue(user)

            etFirstName.setText("")
            etLastName.setText("")
            etAge.setText("")

        }

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