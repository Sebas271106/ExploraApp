package com.juansuarez.exploraapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun navigationApp(){
    val myNavController = rememberNavController()
    NavHost(
        navController = myNavController,
        startDestination = "login",
        modifier = Modifier.fillMaxSize(),
    ) {
        composable("login") {
            LoginScreen(
                onClickRegister = {myNavController.navigate("register")},
                onLoginSuccess = {myNavController.navigate("home")}
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {myNavController.navigate("login")},
                onNavigateToLogin = { myNavController.navigate("login")},
                onBackClick = {myNavController.popBackStack()}
            )
        }
        composable("home"){
            HomeScreen()
        }
    }
}