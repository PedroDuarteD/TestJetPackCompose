package com.pedroduarte.myapplication.data.api.network.repository

import com.pedroduarte.myapplication.data.api.network.service.CardService
import com.pedroduarte.myapplication.data.api.network.Result
import com.pedroduarte.myapplication.data.api.network.models.CardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException

class CardRepositoryImpl (
    private val api: CardService
): CardRepository{

    override suspend fun getCards(): Flow<Result<List<CardModel>>> {
        return flow {
            val projects = try{
                    api.getCards()
            }catch (e: IOException){
                e.printStackTrace();
                emit(Result.Error(message = "Error loading projects"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace();
                emit(Result.Error(message = "Error loading projects"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace();
                emit(Result.Error(message = "Error loading projects"))
                return@flow
            }
            emit(Result.Success(projects))
        }
    }

    override suspend fun setCard(title: String, desc: String): Boolean {
        var Sucess = true
          try {
                api.setCard(CardModel(0,title = title, desc = desc,false))

            }catch (e: Exception){
               Sucess = false
            }

          return Sucess

    }


    override suspend fun updateCard(id: String): Boolean {
        var Sucess = true
        var response = try {
            api.updateCard(id)

        }catch (e: Exception){
            Sucess = false
        }

        return Sucess
    }



}