package com.example.registrofisio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_choose_login.*
import javax.security.auth.login.LoginException

class ChooseLoginView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_login)


        var button_acticity_login_fisioterapeuta = findViewById(R.id.button_login_fisioterapeuta) as Button

        button_acticity_login_fisioterapeuta.setOnClickListener {
            val intent = Intent(this, LoginFisioterapeuta::class.java)
            startActivity(intent);
        }
    }
}
