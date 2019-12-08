package com.modelo.phygital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_sign_up_fisio.*

class SignUpFisio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_fisio)

        val editText_emailRegister: TextInputEditText = findViewById(R.id.editText_emailRegister)
        val editText_userNameRegister: TextInputEditText =
            findViewById(R.id.editText_userNameRegister)
        val editText_passwordRegister: TextInputEditText =
            findViewById(R.id.editText_passwordRegister)
        val editText_confirmPasswordRegister: TextInputEditText =
            findViewById(R.id.editText_confirmPasswordRegister)
        val button_createAccount: Button = findViewById(R.id.button_createAccount)
        val button_toLogin: Button = findViewById(R.id.button_toLogin)
        val textInputLayoutEmailRegister: TextInputLayout =
            findViewById(R.id.textInputLayout_emailRegister)
        val textInputLayoutUserName: TextInputLayout =
            findViewById(R.id.textInputLayout_userNameRegister)
        val textInputLayoutPasswordRegister: TextInputLayout =
            findViewById(R.id.textInputLayout_passwordRegister)
        val textInputLayoutConfirmPasswordRegister: TextInputLayout =
            findViewById(R.id.textInputLayout_confirmPasswordRegister)

        button_createAccount.setOnClickListener {
            //            val intent = Intent(this, NavigationDrawerActivity::class.java)
//            startActivity(intent)
            Toast.makeText(
                this,
                " ${if (validateEmail() && validateUserName() && validatePassword() && validateConfirmPassword()) "EXITO" else "mierda"}",
                Toast.LENGTH_LONG
            ).show()
        }
        button_toLogin.setOnClickListener {
            onBackPressed()
        }

        editText_emailRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEmail()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        editText_userNameRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                validateUserName()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        editText_passwordRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
                validateConfirmPassword()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        editText_confirmPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateConfirmPassword()
            }
        })
    }


    fun validateEmail(): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(editText_emailRegister.text?.trim()).matches() || editText_emailRegister.text.isNullOrEmpty()) {
            textInputLayout_emailRegister.error = "Ingrese un Correo Valido"
            false
        } else {
            textInputLayout_emailRegister.error = null
            true
        }


    }

    fun validateUserName(): Boolean {
        return if (editText_userNameRegister.text.isNullOrBlank()) {
            textInputLayout_userNameRegister.error = "Ingrese un nombre de Usuario"
            false

        } else {
            textInputLayout_userNameRegister.error = null
            true
        }

    }

    fun validatePassword(): Boolean {

        return when {
            editText_passwordRegister.text!!.length < 8 -> {
                textInputLayout_passwordRegister.error = "Minimo 8 Caracteres"
                false
            }
            editText_passwordRegister.text!!.contains(" ") -> {
                textInputLayout_passwordRegister.error = "No debe de haber espacios en blanco"
                false
            }
            else -> {
                textInputLayout_passwordRegister.error = null
                textInputLayout_passwordRegister.helperText = null
                true
            }
        }

    }

    fun validateConfirmPassword(): Boolean {
        var password = editText_passwordRegister.text.toString()
        var confirmPassword = editText_confirmPasswordRegister.text.toString()

        return if (password != confirmPassword || confirmPassword.length < 8) {
            textInputLayout_confirmPasswordRegister.error = "Las contraseñas no coinciden"
            false
        } else {
            textInputLayout_confirmPasswordRegister.error = null
            true
        }
    }
}

