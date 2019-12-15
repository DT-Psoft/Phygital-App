package com.modelo.phygital.AddActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.modelo.phygital.CasoClinicoTabs
import com.modelo.phygital.MenuPrincipal
import com.modelo.phygital.R
import com.modelo.phygital.SesionesActivity
import com.modelo.phygital.tabs.Nota
import com.modelo.phygital.ui.casos_clinicos.CasoClinico

class AddNotas : AppCompatActivity(){
    private lateinit var etTitulo: EditText
    private lateinit var etDescripcion: EditText

    private lateinit var btnAddButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notas)


        etTitulo = findViewById(R.id.titulo_text)
        etDescripcion= findViewById(R.id.descripcion_text)
        btnAddButton = findViewById(R.id.add_button)

        btnAddButton.setOnClickListener {

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("app").child("nota")
            val nota = Nota(
                etTitulo.text.toString().trim(),
                etDescripcion.text.toString().trim()

            )
            usersRef.push().setValue(nota)

            etTitulo.setText("")
            etDescripcion.setText("")


            val intent = Intent(this, CasoClinicoTabs::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, CasoClinicoTabs::class.java)
        startActivity(intent)
        finish()
    }
}