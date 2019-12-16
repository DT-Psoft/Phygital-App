package com.modelo.phygital.AddActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.MenuPrincipal
import com.modelo.phygital.R
import com.modelo.phygital.ui.pacientes.User

class AddPacientes : AppCompatActivity() {


    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etAge : EditText
     private lateinit var etState: EditText
    private lateinit var btnAddButton : Button

    private var users : ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacientes)


        etFirstName = findViewById(R.id.titulo_text)
        etLastName = findViewById(R.id.sesion_numero_text)
        etAge = findViewById(R.id.fecha_sesion_text)

        btnAddButton = findViewById(R.id.add_button)


        btnAddButton.setOnClickListener{

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("users")
            var rnds = (0..1000000).random()
            var state = "activo"

            val user = User(

                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etAge.text.toString().toInt(),
                "activo", rnds.toString()
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
    override fun onBackPressed() {
        val intent = Intent(this, MenuPrincipal::class.java)
        startActivity(intent)
        finish()
    }


}
