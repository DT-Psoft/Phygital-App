package com.example.registrofisio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ChooseLoginView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_login)


        var button_acticity_login_fisioterapeuta : Button =  findViewById(R.id.button_login_fisioterapeuta_menu)

        var button_activity_login_paciente : Button =  findViewById(R.id.button_login_paciente_menu)

        button_acticity_login_fisioterapeuta.setOnClickListener {
            val intentFisio = Intent(this, LoginFisioterapeuta::class.java)
            startActivity(intentFisio);
        }

        button_activity_login_paciente.setOnClickListener {
            val intentPaciente = Intent(this, LoginPaciente::class.java)
            startActivity(intentPaciente);
        }
    }
}
