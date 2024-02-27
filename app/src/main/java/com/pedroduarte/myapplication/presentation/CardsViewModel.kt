package com.pedroduarte.myapplication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroduarte.myapplication.data.api.network.Result
import com.pedroduarte.myapplication.data.api.network.models.CardModel
import com.pedroduarte.myapplication.data.api.network.repository.CardRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardsViewModel(
    private  val cardRepository: CardRepository
) : ViewModel() {



    private val _cards = MutableStateFlow<List<CardModel>>(emptyList())

    val cards = _cards.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()

    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init{
        viewModelScope.launch {
            cardRepository.getCards().collectLatest { result ->
                when (result){
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success ->{
                        result.data?.let { allprojects ->
                            _cards.update { allprojects }
                        }
                    }
                }
            }







        }
    }

    fun addCard(title: String, desc: String): Boolean{
        var created = true
        viewModelScope.launch {
        created=  cardRepository.setCard(title= title, desc= desc)

        }
        return created
    }

    fun updateCard(id: String): Boolean{
        var created = true
        viewModelScope.launch {
            created=  cardRepository.updateCard(id)

        }
        return created
    }




}