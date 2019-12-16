package com.modelo.phygital.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.modelo.phygital.Class.TextValidator
import com.modelo.phygital.R


class SignUpFisio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_fisio)
        val textInputLayout_newFisioEmail: TextInputLayout =
            findViewById(R.id.textInputLayout_newFisioEmail)
        val textInputLayout_newFisioFirstName: TextInputLayout =
            findViewById(R.id.textInputLayout_newFisioFirstName)
        val textInputLayout_newFisioLastName: TextInputLayout =
            findViewById(R.id.textInputLayout_newFisioLastName)
        val textInputLayout_newFisioPassword: TextInputLayout =
            findViewById(R.id.textInputLayout_newFisioPassword)
        val textInputLayout_newFisioConfirmPassword: TextInputLayout =
            findViewById(R.id.textInputLayout_newFisioConfirmPassword)

        var EMAILFLAG = false
        var FNAMEFLAG = false
        var PASSWORDFLAG = false
        var CONFPASSWORDFLAG = true
        var LNAMEFLAG = false

        val button_createAccount: Button = findViewById(R.id.button_newFisioAccount)
        val button_toLogin: Button = findViewById(R.id.button_toLogin)




        button_createAccount.setOnClickListener {
            //            val intent = Intent(this, NavigationDrawerActivity::class.java)
//            startActivity(intent)
            button_createAccount.isEnabled = false
            Toast.makeText(
                this,
                " ${if (EMAILFLAG && FNAMEFLAG && PASSWORDFLAG && LNAMEFLAG && CONFPASSWORDFLAG) "EXITO" else "Error"}",
                Toast.LENGTH_LONG
            ).show()
            button_createAccount.isEnabled = true
        }
        button_toLogin.setOnClickListener {
            onBackPressed()
        }

        textInputLayout_newFisioEmail.editText!!.addTextChangedListener(object :
            TextValidator() {
            override fun validate() {
                EMAILFLAG = validateEmail(textInputLayout_newFisioEmail)
            }
        })
        textInputLayout_newFisioFirstName.editText!!.addTextChangedListener(object :
            TextValidator() {
            override fun validate() {
                FNAMEFLAG = validateUserName(textInputLayout_newFisioFirstName)
            }

        })
        textInputLayout_newFisioLastName.editText!!.addTextChangedListener(object :
            TextValidator() {
            override fun validate() {
                LNAMEFLAG = validateUserName(textInputLayout_newFisioLastName)
            }

        })
        textInputLayout_newFisioPassword.editText!!.addTextChangedListener(object :
            TextValidator() {
            override fun validate() {
                PASSWORDFLAG =
                    validatePassword(textInputLayout_newFisioPassword)
            }

        })
        textInputLayout_newFisioConfirmPassword.editText!!.addTextChangedListener(object :
            TextValidator() {
            override fun validate() {
                CONFPASSWORDFLAG = validateConfirmPassword(
                    textInputLayout_newFisioConfirmPassword,
                    textInputLayout_newFisioPassword.editText!!.text.toString()
                )
            }

        })
    }
}

