package com.modelo.phygital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.pacientes.User

class AddPacientes : AppCompatActivity() {


    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etAge : EditText
    // private lateinit var etState: EditText
    private lateinit var btnAddButton : Button

    private var users : ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacientes)


        etFirstName = findViewById(R.id.frame_text)
        etLastName = findViewById(R.id.lname_text)
        etAge = findViewById(R.id.age_text)
        //   etState = root.findViewById(R.id.state_text)
        btnAddButton = findViewById(R.id.add_button)


        btnAddButton.setOnClickListener{

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("users")
            val user = User(
                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etAge.text.toString().toInt()
                //          etState.text.toString().trim()
            )
            usersRef.push().setValue(user)

            etFirstName.setText("")
            etLastName.setText("")
            etAge.setText("")
            //         etState.setText("")

            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()

        }
    }


}
