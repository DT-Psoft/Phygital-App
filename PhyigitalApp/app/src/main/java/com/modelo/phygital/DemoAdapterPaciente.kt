package com.modelo.phygital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.pacientes.User

class DemoAdapterPaciente(private val users: ArrayList<User>) : RecyclerView.Adapter<DemoAdapterPaciente.DemoViewHolder>() {


    class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var tvFullName: TextView
        private var tvAge: TextView
        private var tvState: TextView
        private var btnDelete: ImageButton
        private var btnEdit: ImageButton


        init {
            tvFullName = view.findViewById(R.id.full_name)
            tvAge = view.findViewById(R.id.age)
            tvState = view.findViewById(R.id.state)
            btnDelete = view.findViewById(R.id.delete_button)
            btnEdit = view.findViewById(R.id.edit_button)



        }

        public fun bind(user: User) {
            tvFullName.setText("${user.lastName}, ${user.firstName}")
            tvAge.setText("${user.age}")
            tvState.setText("${user.state}")
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("users")

            btnDelete.setOnClickListener{

                usersRef.child(user.id!!).removeValue()
                val snack = Snackbar.make(it,"Paciente eliminado con exito", Snackbar.LENGTH_LONG)

            }
            btnEdit.setOnClickListener{
                val snack = Snackbar.make(it,"Esto sera para editar, aun no se como saldra",
                    Snackbar.LENGTH_LONG)
                snack.show()
            }

        }



    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_demo_holder_paciente, parent, false) as View

        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}