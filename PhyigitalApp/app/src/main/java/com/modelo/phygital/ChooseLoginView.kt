package com.modelo.phygital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.annotation.SuppressLint
import com.facebook.stetho.Stetho

class ChooseLoginView : AppCompatActivity() {

    @SuppressLint("NewApi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_choose_login)


        var button_acticity_login_fisioterapeuta : Button =  findViewById(R.id.button_login_fisioterapeuta_menu)

        var button_activity_login_paciente : Button =  findViewById(R.id.button_login_paciente_menu)

        // => Inicializa librería Stetho
        Stetho.initializeWithDefaults(this)

        // => Obtener referencia a base de datos basada en librería Room
        val db = AppDatabase.getAppDatabase(this)
        //better performance?


        button_acticity_login_fisioterapeuta.setOnClickListener {
            val intentFisio = Intent(this, LoginFisioterapeuta::class.java)
            startActivity(intentFisio);
        }

        button_activity_login_paciente.setOnClickListener {
            val intentPaciente = Intent(this, LoginPaciente::class.java)
            startActivity(intentPaciente)

        }
    }
}
