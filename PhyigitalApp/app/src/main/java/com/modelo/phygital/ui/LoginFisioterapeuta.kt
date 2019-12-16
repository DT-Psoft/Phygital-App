package com.modelo.phygital.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.modelo.phygital.Class.TextValidator
import com.modelo.phygital.R


class LoginFisioterapeuta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_fisioterapeuta)
        var EMAILFLAG = false
        var PASSWORDFLAG = false

        val button_fisioLogIn: Button = findViewById(R.id.button_fisioLoginToMenu)

        val button_fisioSignUp: Button = findViewById(R.id.button_fisioLoginToNewAccount)
        button_fisioSignUp.setOnClickListener {

            val intentMenu = Intent(this, SignUpFisio::class.java)
            startActivity(intentMenu)
        }

        val textInputLayout_email: TextInputLayout =
            findViewById(R.id.textInputLayout_fisioEmailLogin)
        textInputLayout_email.editText!!.addTextChangedListener(object : TextValidator() {
            override fun validate() {
                validateEmail(textInputLayout_email)
            }
        })

        val textInputLayout_password: TextInputLayout =
            findViewById(R.id.textInputLayout_fisioPasswordLogin)
        textInputLayout_password.editText!!.addTextChangedListener(object : TextValidator() {
            override fun validate() {
                validatePassword(textInputLayout_password)
            }
        })

    }

    override fun onBackPressed() {
        messageAlert("Seguro que deseas salir?", "Los datos en los campos se perderan") { super.onBackPressed() }
    }

    private fun messageAlert(title: String, message: String, action: () -> Unit) {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Si", DialogInterface.OnClickListener { _, _ ->
                //set what would happen when positive button is clicked

                action()
            })
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { _, _ ->
                //set what should happen when negative button is clicked

            })
            .show()

    }
}


/*

        //this.deleteDatabase("quizzapp.db")


        // => Inicializa librería Stetho
        Stetho.initializeWithDefaults(this)

        // => Obtener referencia a base de datos basada en librería Room
        val db = AppDatabase.getAppDatabase(this)
        //better performance?

        val btnOpenMenu: Button = findViewById(R.id.button_login_fisioterapeuta_menu)
        val text_input_correo: TextInputEditText = findViewById(R.id.editText_emailLogin)
        val text_input_contraseña_correo: TextInputEditText = findViewById(R.id.editText_passwordLogin)

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
}*/
