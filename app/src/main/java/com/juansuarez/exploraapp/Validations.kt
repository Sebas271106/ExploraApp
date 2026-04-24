package com.juansuarez.exploraapp

import android.util.Patterns

fun validateEmail(email: String): Pair<Boolean, String> {
    return when {
        email.isEmpty() -> Pair(false, "El correo electrónico no puede estar vacío")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo electrónico no es válido")           // Libreria de Java para el correo
        !email.contains("@") -> Pair(false, "El correo electrónico no es válido")
        else -> Pair(true, "")
    }
}

fun validatePassword(password: String): Pair<Boolean, String> {
    return when{
        password.isEmpty() -> Pair(false, "La contraseña es requerida")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres")
        !password.any { it.isDigit() } -> Pair(false, "La contraseña debe contener al menos un número")
        else -> Pair(true, "")
    }
}