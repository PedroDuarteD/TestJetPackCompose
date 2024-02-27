package com.pedroduarte.myapplication

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pedroduarte.myapplication.Retrofit.RetrofitInstance
import com.pedroduarte.myapplication.activity.AddCardActivity
import com.pedroduarte.myapplication.data.api.network.models.CardModel
import com.pedroduarte.myapplication.data.api.network.repository.CardRepository
import com.pedroduarte.myapplication.data.api.network.repository.CardRepositoryImpl
import com.pedroduarte.myapplication.presentation.CardsViewModel
import com.pedroduarte.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

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

                    Greeting(viewModel, context)
                    LaunchedEffect(key1 = viewModel.showErrorToastChannel,){
                        viewModel.showErrorToastChannel.collectLatest { show ->
                            if(show){
                                Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(viewModel: CardsViewModel, context: Context) {

     var numeros =  remember {mutableStateListOf<CardModel>() }

    if(viewModel.cards.collectAsState().value.size!=0){
        for(item in viewModel.cards.collectAsState().value){
                numeros.add(item)
        }

    }


    MyApplicationTheme {
        Scaffold(
            topBar = {
                     TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {  context.startActivity(Intent(context, AddCardActivity::class.java)) }) {
                    Icon(Icons.Filled.Add, "Add card")

                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 65.dp),
            ) {

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.White,
                ) {

                    if(numeros.isEmpty()){
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Box(modifier = Modifier.padding(5.dp)){
                                Text(stringResource(id = R.string.app_emp_list), color = Color.Black, style = TextStyle(fontSize = 40.sp))
                            }
                        }

                    }else{
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ){

                            items(items = numeros){ card->
                                Card(
                                    onClick = {

                                        val response = viewModel.updateCard(card.id.toString())
                                        if(response){
                                            numeros[numeros.indexOf(card)] = CardModel(card.id,card.title, card.desc, if(card.open) false else true)
                                        }
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
                                                card.title ,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                painter = painterResource(id = if(card.open) R.drawable.arrow_top else R.drawable.arrow_bottom),
                                                contentDescription = stringResource(id = R.string.app_name)
                                            )
                                        }
                                        if (card.open) {
                                            Text(card.desc)
                                        }
                                    }

                                }

                            }






                        }
                    }

                 }

                }
            }

        }

    }






@Preview(showBackground = true, locale = "en")
@Composable
fun GreetingPreview() {
    Text(stringResource(id = R.string.app_name))
}