package com.juansuarez.exploraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val myNavController = rememberNavController()
            NavHost(
                navController = myNavController,
                startDestination = "login",
                modifier = Modifier.fillMaxSize(),
            ) {
                composable("login") {
                    LoginScreen(onLoginSuccess = {myNavController.navigate("register")}, onNavigateToRegister = { myNavController.navigate("register")})
                }
                composable("register") {
                    RegisterScreen(onRegisterSuccess = {myNavController.navigate("login")}, onNavigateToLogin = { myNavController.navigate("login")}, onBackClick = {myNavController.popBackStack()})
                }
            }
        }
    }
}

