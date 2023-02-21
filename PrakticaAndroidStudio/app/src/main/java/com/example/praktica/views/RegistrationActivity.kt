package com.example.praktica.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktica.models.RegistrationAccount
import com.example.praktica.views.ui.theme.PrakticaTheme

private var isShowPassword = mutableStateOf(false)
private var isShowPasswordRepeat = mutableStateOf(false)
private var login = mutableStateOf("")
private var password = mutableStateOf("")
private var passwordRepeat = mutableStateOf("")
private  var registrationActivity: RegistrationActivity? = null

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationActivity = this@RegistrationActivity
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Registration()
                }
            }
        }
    }
}

@Composable
fun Registration() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(80.dp, 200.dp)){
        Text(text = "Регистрация", fontSize = 26.sp, modifier = Modifier.align(Alignment.TopCenter))
        Column(modifier = Modifier.align(Alignment.Center)) {
            TextField(value = login.value, onValueChange = {value -> login.value = value}, label = { Text(
                text = "Логин"
            )})
            TextField(value = password.value, onValueChange = {value -> password.value = value}, label = { Text(
                text = "Пароль"
            )}, modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp),
                visualTransformation = if(isShowPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isShowPassword.value = !isShowPassword.value }) {
                        val image = if (isShowPassword.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        Icon(imageVector  = image, "")
                    }
                })

            TextField(value = passwordRepeat.value, onValueChange = {value -> passwordRepeat.value = value}, label = { Text(
                text = "Повторите пароль"
            )}, modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp),
                visualTransformation = if(isShowPasswordRepeat.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isShowPasswordRepeat.value = !isShowPasswordRepeat.value }) {
                    val image = if (isShowPasswordRepeat.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    Icon(imageVector  = image, "")
                }
            })

            Button(onClick = {
                if(login.value.trim() != "" && password.value.trim() != ""){
                    if(password.value == passwordRepeat.value){
                        RegistrationAccount(login.value, password.value, onRegistration = {result ->

                            if(result == "Аккаунт успешно создан"){
                                registrationActivity!!.finish()
                            }
                        })
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                Text(text = "Зарегистрироваться")
            }

            TextButton(onClick = {

                registrationActivity!!.finish()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Авторизация")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    PrakticaTheme {
        Registration()
    }
}