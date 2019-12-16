package com.modelo.phygital.ui.AddActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.ui.MenuPrincipal
import com.modelo.phygital.R
import com.modelo.phygital.ui.casos_clinicos.CasoClinico
import com.modelo.phygital.ui.casos_clinicos.CasosClinicosViewModel

class AddCasosClinicos : AppCompatActivity() {


    private lateinit var etPinPaciente: EditText
    private lateinit var etPacientePadencia: EditText

    private lateinit var btnAddButton: Button

    private var casos: ArrayList<CasoClinico> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_casos_clinicos)


        etPinPaciente = findViewById(R.id.titulo_text)
        etPacientePadencia= findViewById(R.id.sesion_numero_text)
        btnAddButton = findViewById(R.id.add_button)

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


            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }
}

