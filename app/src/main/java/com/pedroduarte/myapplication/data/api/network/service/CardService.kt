package com.pedroduarte.myapplication.data.api.network.service

import com.pedroduarte.myapplication.data.api.network.models.CardModel
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CardService {
    @GET("cards")
    suspend fun getCards(
   //     @Query("myadmin") myadmin: String
    ) : List<CardModel>

    @POST("addCard")
    suspend fun setCard(
      @Body post: CardModel
    ) : Boolean

    @PUT("updateOpen/{id}")
    suspend fun updateCard(
        @Query("id") id: String
    ) : Boolean



    companion object{
        val BASE_URL ="https://api.pedroduarte.online/api/"
    }
}