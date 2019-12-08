package com.modelo.phygital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.modelo.phygital.Entities.UserETY
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_login_fisioterapeuta.*


class LoginFisioterapeuta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_fisioterapeuta)

        //this.deleteDatabase("quizzapp.db")


        // => Inicializa librería Stetho
        Stetho.initializeWithDefaults(this)

        // => Obtener referencia a base de datos basada en librería Room
        val db = AppDatabase.getAppDatabase(this)
        //better performance?

        val btnOpenMenu: Button = findViewById(R.id.button_login_fisioterapeuta_menu)

        val btnOpenSignUpFisio: Button = findViewById(R.id.button_CreateNewAccount)
        btnOpenSignUpFisio.setOnClickListener{

            val intentMenu = Intent(this, SignUpFisio::class.java)
            startActivity(intentMenu)
        }

        btnOpenMenu.setOnClickListener {
            val username2: UserETY? =
                db.UserDAO().getUserByName(text_input_correo.text.toString(), text_input_contraseña_correo.text.toString())

            //      for(i in username2.indices) {
            val login = username2
            var useridLogged = db.UserDAO().getUserByIsLogged()

            if (login == null) {
                Toast.makeText(
                    this,
                    "Datos incorrectos",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                //useridLogged.is_logged = 1
                if (useridLogged == null) {
                    login.is_logged = 1
                    db.UserDAO().UpdateUser(login)
                } else {
                    useridLogged.is_logged = 0
                    login.is_logged = 1
                    db.UserDAO().UpdateUser(useridLogged)
                    db.UserDAO().UpdateUser(login)
                }

                val intentMenu = Intent(this, MenuPrincipal::class.java)
                startActivity(intentMenu)
            }
        }
    }
}
