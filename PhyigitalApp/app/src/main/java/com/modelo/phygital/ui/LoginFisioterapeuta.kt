package com.modelo.phygital.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.textfield.TextInputLayout
import com.modelo.phygital.Class.AppDatabase
import com.modelo.phygital.Class.TextValidator
import com.modelo.phygital.Entities.UserETY
import com.modelo.phygital.R
import org.json.JSONArray
import java.net.URL

class AsyncTaskExample(val email:String, val pass:String) : AsyncTask<String, String, String>() {

    override fun onPreExecute() {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun doInBackground(vararg p0: String?): String? {
        val url = URL("http://187.147.89.178:81/api/physioterapist/login/${email}/${pass}").readText()
        val error = "Error"
        Log.d("hola",url)
        if(url != "Datos incorectos")
        {
            return null

        }
        else
        {
            val array = JSONArray(url)
            var result = array.getJSONObject(0).getString("user_name") + array.getJSONObject(0).getString("password")
            return array.getJSONObject(0).getString("user_name") + array.getJSONObject(0).getString("password")
        }
    }


    override fun onPostExecute(result: String?) {

    }
}

class LoginFisioterapeuta : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_fisioterapeuta)
        this.deleteDatabase("fisio.db")
        val db = AppDatabase.getAppDatabase(this)


        var EMAILFLAG = false
        var PASSWORDFLAG = false

        val button_fisioLogIn: Button = findViewById(R.id.button_fisioLoginToMenu)
        val button_fisioSignUp: Button = findViewById(R.id.button_fisioLoginToNewAccount)
        button_fisioSignUp.setOnClickListener {
            messageAlert("¿Desea ir al Registro?", "") {
                val intentMenu = Intent(this, SignUpFisio::class.java)
                startActivity(intentMenu)
            }
        }

        logIn()

        val textInputLayout_email: TextInputLayout =
            findViewById(R.id.textInputLayout_fisioEmailLogin)
        textInputLayout_email.editText!!.addTextChangedListener(object : TextValidator() {
            override fun validate() {
                EMAILFLAG = validateUserName(textInputLayout_email)
            }
        })

        val textInputLayout_password: TextInputLayout =
            findViewById(R.id.textInputLayout_fisioPasswordLogin)
     //   textInputLayout_password.editText!!.addTextChangedListener(object : TextValidator() {
     //       override fun validate() {
     //           PASSWORDFLAG = validatePassword(textInputLayout_password)
      //      }
     //   })

        button_fisioLogIn.setOnClickListener {
        //    if (EMAILFLAG && PASSWORDFLAG) {
                try {
                    val email = textInputLayout_email.editText!!.text.toString()
                    val password = textInputLayout_password.editText!!.text.toString()
                    val currentUser: UserETY? = db.UserDAO().getUserByName(email, password)
                    val current = AsyncTaskExample(email, password).execute()
                    if (current != null) {
                        val intentMenu = Intent(this, MenuPrincipal::class.java)
                        startActivity(intentMenu)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        e.localizedMessage!! + e.cause.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
         //   }
        }

    }

    override fun onBackPressed() {
        messageAlert(
            "Seguro que deseas salir?",
            "Los datos en los campos se perderan"
        ) { super.onBackPressed() }
    }

    private fun messageAlert(title: String, message: String, action: () -> Unit) {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { _, _ ->
                //set what would happen when positive button is clicked
                action()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                //set what should happen when negative button is clicked

            }
            .show()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun logIn() {

    }
}


/*

        //


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
