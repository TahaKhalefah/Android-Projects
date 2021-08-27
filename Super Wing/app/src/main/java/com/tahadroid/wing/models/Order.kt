package com.tahadroid.wing.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders_table")
data class Order(
    var placeKey:String,
    var placeName: String,
    var title: String,
    var image: String,
    var price: Double,
    var quantity: Int,
    var size: Int,
    var tag: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null
}