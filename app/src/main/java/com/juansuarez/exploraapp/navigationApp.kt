package com.juansuarez.exploraapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun navigationApp() {

    val myNavController = rememberNavController()
    var myStartDestination: String = "login"

    // Mantener Sesion Activa
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser != null) {
        myStartDestination = "home"
    } else {
        myStartDestination = "login"
    }
    NavHost(
        navController = myNavController,
        startDestination = myStartDestination,
        modifier = Modifier.fillMaxSize(),
    ) { 
        composable("login") {
            LoginScreen(
                onClickRegister = { myNavController.navigate("register") },
                onLoginSuccess = {
                    myNavController.navigate("home") {
                        popUpTo("login") {inclusive = true}             // Para limpiar stack
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { myNavController.navigate("login") },
                onRegisterSuccess = {
                    myNavController.navigate("home") {
                        popUpTo("register") {inclusive = true}
                    }
                },
                onBackClick = { myNavController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(
                onClickLogout = {
                    myNavController.navigate("login"){
                        popUpTo("home") {inclusive = true}
                    }
                }
            )
        }
    }
}