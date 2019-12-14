package com.modelo.phygital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class AddSesion : AppCompatActivity() {

    private lateinit var etPacienteSesion: EditText
    private lateinit var etSesion: EditText
    private lateinit var etFechaSesion: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etActividades: EditText

    private lateinit var btnAddButton: Button


    private var sesiones: ArrayList<Sesion> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sesion)

        etPacienteSesion = findViewById(R.id.paciente_sesion_text)
        etSesion = findViewById(R.id.sesion_numero_text)
        etFechaSesion = findViewById(R.id.fecha_sesion_text)
        etDescripcion = findViewById(R.id.descripcion_text)
        etActividades = findViewById(R.id.actividades_text)
        btnAddButton = findViewById(R.id.add_sesion_button)



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

            val intent = Intent(this, SesionesActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}
