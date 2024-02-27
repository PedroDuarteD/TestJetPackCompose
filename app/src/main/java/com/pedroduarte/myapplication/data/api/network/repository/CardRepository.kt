package com.pedroduarte.myapplication.data.api.network.repository

import com.pedroduarte.myapplication.data.api.network.Result
import com.pedroduarte.myapplication.data.api.network.models.CardModel
import kotlinx.coroutines.flow.Flow;
import okhttp3.Response

interface  CardRepository {
    suspend fun getCards(): Flow<Result<List<CardModel>>>

    suspend fun setCard(title: String ,desc: String): Boolean
    suspend fun updateCard(id: String): Boolean
}