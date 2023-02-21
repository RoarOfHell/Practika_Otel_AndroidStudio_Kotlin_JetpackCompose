package com.example.praktica.views

import android.content.Intent
import android.os.Bundle
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
import com.example.praktica.models.LoginAccount
import com.example.praktica.views.ui.theme.PrakticaTheme
import kotlin.math.log

private var isShowPassword = mutableStateOf(false)
private var login = mutableStateOf("")
private var password = mutableStateOf("")
private var autorizationActivity: AutorizationActivity? = null

class AutorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autorizationActivity = this@AutorizationActivity
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AutorizationWindow()
                }
            }
        }
    }
}

@Composable
fun AutorizationWindow() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(80.dp, 200.dp)){
        Text(text = "Авторизация", fontSize = 26.sp, modifier = Modifier.align(Alignment.TopCenter))
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
            
            Button(onClick = {
                LoginAccount(login = login.value, password = password.value, onAutorization = {value ->
                    if(value == "Успешно"){
                        val loadingActivity = Intent(autorizationActivity!!, LoadingActivity::class.java)
                        autorizationActivity!!.startActivity(loadingActivity)
                        autorizationActivity!!.finish()
                    }
                }, activity = autorizationActivity!!)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                Text(text = "Войти")
            }

            TextButton(onClick = {
                val registrationActivity = Intent(autorizationActivity!!, RegistrationActivity::class.java)
                autorizationActivity!!.startActivity(registrationActivity)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Регистрация")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    PrakticaTheme {
        AutorizationWindow()
    }
}