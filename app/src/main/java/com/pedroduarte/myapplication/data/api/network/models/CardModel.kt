package com.pedroduarte.myapplication.data.api.network.models

data class CardModel (
    val id: Int,
    val title: String,
    val desc: String,
    var open: Boolean
)