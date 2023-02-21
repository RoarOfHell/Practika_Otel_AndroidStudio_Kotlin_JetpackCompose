package com.example.praktica.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.praktica.MainActivity
import com.example.praktica.models.LoadUserAccount
import com.example.praktica.models.LoginAccount
import com.example.praktica.views.ui.theme.PrakticaTheme

private var checkAccountActivity: CheckAccountActivity? = null

class CheckAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAccountActivity = this@CheckAccountActivity
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting4()
                }
            }
        }
    }
}

@Composable
fun Greeting4() {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Практика")
            LoadUserAccount(checkAccountActivity!!, onLoadUserInfo = {login, password ->
                println("${login} : ${password}")
                LoginAccount(checkAccountActivity!! ,login, password, onAutorization = {result ->
                    if(result == "Не верный логин или пароль"){
                        val autorization = Intent(checkAccountActivity!!, AutorizationActivity::class.java)
                        checkAccountActivity!!.startActivity(autorization)
                        checkAccountActivity!!.finish()
                    }
                    else if(result == "Успешно"){
                        val loadingActivity = Intent(checkAccountActivity!!, LoadingActivity::class.java)
                        checkAccountActivity!!.startActivity(loadingActivity)
                        checkAccountActivity!!.finish()
                    }
                })
            } )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    PrakticaTheme {
        Greeting4()
    }
}