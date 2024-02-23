package com.pedroduarte.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedroduarte.myapplication.ui.theme.MyApplicationTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {

    var context = LocalContext.current
    var numero  by remember { mutableStateOf(0) }
    var numeros  = ArrayList<String>()
    var text  by remember { mutableStateOf<String>("") }


    MyApplicationTheme {
        Surface (
            color = Color.White
        ){

            Column (
                modifier = Modifier.fillMaxSize()
            ){

                Surface (
                    color = Color.White,
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())

                ){
                    Column (

                    ){
                for (item in numeros){
                    renderContainer(name = item)

                }
                    }
                }


                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(numero.toString(), color = Color.Black, fontSize = 30.sp, modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
                        Row(
                        ) {

                            Button(onClick = {

                                if(text==""){
                                    Toast.makeText(context,"Name is Empty !",Toast.LENGTH_SHORT).show()
                                }else{
                                    numero+=1
                                    numeros.add(text+" "+numero.toString())
                                    text = ""
                                }


                            }) {
                                Text("Adicionar")
                            }
                        }

                        Column (
                            modifier = Modifier.padding(top = 20.dp)
                        ){
                            Text("Card Name")
                            TextField(
                                value = text,
                                onValueChange = { nextText: String ->
                                    text = nextText},
                            )

                        }


                    }
            }

        }

    }


}



@Composable
fun renderContainer(name:String , show: Boolean= false){

    var mycolor = MaterialTheme.colorScheme.primary

    if(show){
        mycolor = MaterialTheme.colorScheme.secondary
    }


    Surface (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(100.dp),
          color =  mycolor,
    border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(10.dp)
    ){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(name, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting( )
}