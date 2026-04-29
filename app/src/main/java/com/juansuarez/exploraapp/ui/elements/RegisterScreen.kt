package com.juansuarez.exploraapp

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.juansuarez.exploraapp.ui.theme.ExploraAppTheme

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    // Estados para los inputs
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputConfirmPassword by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    // Colores de la app
    val primaryOrange = Color(0xFFE45D25)
    val lightGrayBg = Color(0xFFF8F9FE)
    val inputBg = Color(0xFFE5E5EA)

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    // Manejo de errores
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = lightGrayBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.Start)
                    .offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = primaryOrange
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Explorando Colombia",
                color = primaryOrange,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Crea tu cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Empieza tu aventura por el realismo mágico",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                RegisterField(
                    label = "NOMBRE COMPLETO",
                    value = inputName,
                    onValueChange = { inputName = it },
                    placeholder = "Tu nombre",
                    leadingIcon = Icons.Default.Person,
                    inputBg = inputBg,
                    isError = nameError.isNotEmpty(),
                    errorMessage = nameError
                )

                Spacer(modifier = Modifier.height(20.dp))

                RegisterField(
                    label = "CORREO ELECTRÓNICO",
                    value = inputEmail,
                    onValueChange = { inputEmail = it },
                    placeholder = "hola@ejemplo.com",
                    leadingIcon = Icons.Default.Email,
                    inputBg = inputBg,
                    isError = emailError.isNotEmpty(),
                    errorMessage = emailError
                )

                Spacer(modifier = Modifier.height(20.dp))

                RegisterField(
                    label = "CONTRASEÑA",
                    value = inputPassword,
                    onValueChange = { inputPassword = it },
                    placeholder = "........",
                    leadingIcon = Icons.Default.Lock,
                    inputBg = inputBg,
                    isPassword = true,
                    isError = passwordError.isNotEmpty(),
                    errorMessage = passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))

                RegisterField(
                    label = "CONFIRMAR",
                    value = inputConfirmPassword,
                    onValueChange = { inputConfirmPassword = it },
                    placeholder = "........",
                    leadingIcon = Icons.Default.Refresh,
                    inputBg = inputBg,
                    isPassword = true,
                    isError = confirmPasswordError.isNotEmpty(),
                    errorMessage = confirmPasswordError
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptedTerms,
                    onCheckedChange = { acceptedTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = primaryOrange)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Acepto los ")
                        withStyle(
                            style = SpanStyle(
                                color = primaryOrange,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("términos y condiciones")
                        }
                        append(" así como el tratamiento de datos personales.")
                    },
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val isValidName = validateName(inputName).first
                    val isValidEmail = validateEmail(inputEmail).first
                    val isValidPassword = validatePassword(inputPassword).first
                    val isValidConfirmPassword =
                        validatePasswordConfirm(inputPassword, inputConfirmPassword).first


                    nameError = validateName(inputName).second
                    emailError = validateEmail(inputEmail).second
                    passwordError = validatePassword(inputPassword).second
                    confirmPasswordError = validatePasswordConfirm(inputPassword, inputConfirmPassword).second

                    if (isValidName && isValidEmail && isValidPassword && isValidConfirmPassword) {
                        auth.createUserWithEmailAndPassword(inputEmail, inputPassword).
                                addOnCompleteListener(activity) { task ->
                                    if (task.isSuccessful) {
                                        onRegisterSuccess()
                                    } else {
                                        registerError = when(task.isSuccessful){
                                            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrecta"
                                            is FirebaseAuthUserCollisionException -> "Este correo ya está registrado"
                                            else -> "Error al registrarse"
                                        }
                                    }
                                }
                    } else {
                        registerError = "Error al registrarse"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(primaryOrange, Color(0xFFFF8A65))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
                Text(
                    text = " O REGÍSTRATE CON ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialButton(
                    text = "Google",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Email
                )
                SocialButton(
                    text = "Apple",
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Lock
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(text = "¿Ya tienes una cuenta? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Inicia sesión",
                    color = primaryOrange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}

@Composable
fun RegisterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    inputBg: Color,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = modifier) {

        // 🔹 Label
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Campo de texto
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp)),
            placeholder = { Text(placeholder, color = Color.Gray) },
            leadingIcon = {
                Icon(
                    leadingIcon,
                    contentDescription = null,
                    tint = if (isError) MaterialTheme.colorScheme.error else Color.Gray
                )
            },
            visualTransformation = if (isPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
            ),
            isError = isError, // ✅ activa estado de error
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBg,
                unfocusedContainerColor = inputBg,
                disabledContainerColor = inputBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            singleLine = true
        )

        // 🔹 Mensaje de error
        if (isError && errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ExploraAppTheme() {
        RegisterScreen(onRegisterSuccess = {}, onNavigateToLogin = {})
    }
}
