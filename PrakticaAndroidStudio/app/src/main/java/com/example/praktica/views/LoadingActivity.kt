package com.example.praktica.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktica.MainActivity
import com.example.praktica.views.ui.theme.PrakticaTheme
import java.util.concurrent.ConcurrentLinkedDeque

private var loadingActivity: LoadingActivity? = null

class LoadingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingActivity = this@LoadingActivity
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Loading()
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally){
        val strokeWidth = 5.dp

        CircularProgressIndicator(
            modifier = Modifier.drawBehind {
                drawCircle(
                    Color.Red,
                    radius = size.width / 2 - strokeWidth.toPx() / 2,
                    style = Stroke(strokeWidth.toPx())
                )
            },
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
        
        Text(text = "Ожидайте загрузку", fontSize = 18.sp)
        
        WaitNextWindow(onWaitNext = {val mainActivity = Intent(loadingActivity!!, MainActivity::class.java)
            loadingActivity!!.startActivity(mainActivity)
            loadingActivity!!.finish()})
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    PrakticaTheme {
        Loading()
    }
}

fun WaitNextWindow(onWaitNext: () -> Unit){
    Thread{
        Thread.sleep(2000)
        onWaitNext()
    }.start()
}