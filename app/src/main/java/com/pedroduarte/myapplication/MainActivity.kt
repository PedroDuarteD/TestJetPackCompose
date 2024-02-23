package com.pedroduarte.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedroduarte.myapplication.ui.theme.MyApplicationTheme
import com.pedroduarte.myapplication.ui.theme.MyCard

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

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {

    var context = LocalContext.current
   var numero  by remember { mutableStateOf(0) }

    var text  by remember { mutableStateOf("") }

     var numeros = remember {mutableStateListOf<MyCard>() }

    numeros.add(MyCard("First",true))


    MyApplicationTheme {
        Surface(
            color = Color.White
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Surface(
                    color = Color.White,
                ) {
                    LazyColumn{

                        items(items = numeros){ card->
                            Card(
                                onClick = {
                                    numeros[numeros.indexOf(card)] = MyCard(card.name, if(card.open) false else true)
                                },
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .height(if (card.open) 90.dp else 50.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    MaterialTheme.colorScheme.secondaryContainer
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {

                                        Text(
                                            card.name,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Icon(
                                            painter = painterResource(id = if(card.open) R.drawable.arrow_top else R.drawable.arrow_bottom),
                                            contentDescription = stringResource(id = R.string.app_name)
                                        )
                                    }
                                    if (card.open) {
                                        Text("Render Text")
                                    }
                                }

                            }

                        }






                }
                 }


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                      Text(numero.toString(), color = Color.Black, fontSize = 30.sp, modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))



                        renderButton (
                            click = {
                                if(text==""){
                                    Toast.makeText(context,"Name is Empty !",Toast.LENGTH_SHORT).show()
                                }else{
                                    numero+=1
                                    numeros.add(MyCard(text, false))
                                    text = ""
                                }
                            },
                            "Adicionar",
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Red,
                                    Color.Yellow
                            )
                            )
                        )


                    }

                    Column(
                        modifier = Modifier.padding(top = 20.dp)
                    ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { nextText: String ->
                    text = nextText
                },
                label = {
                    Text("Card Name")
    })


                    }


                }
            }

        }

    }




@Composable
fun renderButton(click: ()-> Unit, text: String, gradient: Brush){
    Button(
        onClick = click,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20),
        contentPadding = PaddingValues()
        ) {
    Box(
    modifier = Modifier
        .background(gradient)
        .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ){
        Text(text)
    }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}