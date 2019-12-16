package com.modelo.phygital.Class

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


abstract class TextValidator : TextWatcher {

    protected fun validateEmail(email: TextInputLayout): Boolean {

        val text = email.editText!!.text.toString().trim()
        return if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            email.error = "Correo no Valido"
            false
        } else {
            email.error=""
            true
        }
    }

    protected fun validateConfirmPassword(confirmPassView: TextInputLayout, password: String): Boolean {
        val confirmPass = confirmPassView.editText!!.text.toString()
        return when {
            confirmPass.isNullOrBlank() -> {
                confirmPassView.error = "Campo Obligatorio"; false
            }
            confirmPass != password -> {
                confirmPassView.error = "Las Contraseñas no coinciden"; false
            }
            else -> {
                confirmPassView.error = ""; true
            }
        }
    }

    protected fun validatePassword(passView: TextInputLayout): Boolean {
        val password = passView.editText!!.text.toString()
        val regex = "^[a-zA-Z0-9]+$"
        return when {
            password.isNullOrBlank() -> {
                passView.error = "Campo Obligatorio"; false
            }
            password.length < 8 -> {
                passView.error = "Minimo 8 Caracteres"; false
            }
            !Pattern.compile(regex).matcher(password).matches() -> {
                passView.error = "Usa solo Numeros y Letras"; false
            }
            else -> {
                passView.error = ""; true
            }
        }


    }

    protected fun validateUserName(userName: TextInputLayout): Boolean {
        val text = userName.editText!!.text.toString()
        val regex = "^[ña-zA-Z _]+$"
        return when {
            text.isNullOrBlank() -> {
                userName.error = "Campo Obligatorio"; false
            }
            text.count { x -> x == ' ' } >= 2 -> {
                userName.error = "Hay más de un espacio"; false
            }
            !Pattern.compile(regex).matcher(text).matches()->{
                userName.error = "Solo Letras"; false
            }
            else -> {
                userName.error = ""; true
            }
        }
    }

    abstract fun validate()

    override fun afterTextChanged(s: Editable) {
        validate()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//To change body of created functions use File | Settings | File Templates.
    }

}