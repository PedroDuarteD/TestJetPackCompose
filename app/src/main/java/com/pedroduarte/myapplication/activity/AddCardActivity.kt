package com.pedroduarte.myapplication.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroduarte.myapplication.R
import com.pedroduarte.myapplication.Retrofit.RetrofitInstance
import com.pedroduarte.myapplication.activity.ui.theme.MyApplicationTheme
import com.pedroduarte.myapplication.data.api.network.repository.CardRepositoryImpl
import com.pedroduarte.myapplication.presentation.CardsViewModel

class AddCardActivity : ComponentActivity() {

    private val viewModel by viewModels<CardsViewModel> (factoryProducer = {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CardsViewModel(CardRepositoryImpl(RetrofitInstance.api)) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var context = LocalContext.current
                    val activity = (LocalContext.current as? Activity)

                    if (activity != null) {
                        Greeting2(viewModel, context, activity)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting2(viewModel: CardsViewModel, context: Context, activity: Activity) {

    var edit_title by remember { mutableStateOf("") }
    var edit_desc by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.title_activity_add_card)) })
        }
    ){

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ){
            TextField(
                modifier = Modifier.padding(5.dp),
                value = edit_title,
                label = {
                    Text(stringResource(id = R.string.card_title))
                },
                onValueChange = {
                    edit_title = it
                }
            )

            TextField(
                modifier = Modifier.padding(5.dp),
                value = edit_desc,
                label = {
                    Text(stringResource(id = R.string.card_desc))
                },
                onValueChange = {
                    edit_desc = it
                }
            )
            Row (
            ){

                renderButton (
                    click = {
                        if(edit_title!="" && edit_desc!=""){
                            val response=  viewModel.addCard(edit_title,edit_desc)
                            if(response){
                                activity.finish()
                            }else{
                                Toast.makeText(context,"Erro ao criar ",Toast.LENGTH_SHORT).show()
                            }

                        }else{
                            Toast.makeText(context,"Falta algo !", Toast.LENGTH_SHORT).show()
                        }
                    },
                    stringResource(id = R.string.card_btn),
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow
                        )
                    )

                )
            }
        }
    }

}
@Composable
fun renderButton(click: ()-> Unit, text: String, gradient: Brush){
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = click,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20),
    ) {
        Box(
            modifier = Modifier.run {
                background(gradient)
                    .width(280.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            },
            contentAlignment = Alignment.Center
        ){
            Text(text)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApplicationTheme {
    }
}