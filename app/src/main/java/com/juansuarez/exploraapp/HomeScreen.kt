package com.juansuarez.exploraapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(){
    Text(
        "Hola",
        color = colorResource(id = R.color.white),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}