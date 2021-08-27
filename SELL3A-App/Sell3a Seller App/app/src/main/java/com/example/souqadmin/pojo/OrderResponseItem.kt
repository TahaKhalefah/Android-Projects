package com.example.souqadmin.pojo

import java.io.Serializable

data class OrderResponseItem(
    val Address: String,
    val __v: Int,
    val _id: String,
    val importCharge: Int,
    val itemIds: List<ItemIdX>,
    val orderCode: String,
    val orderDate: String,
    val orderState: String,
    val totalPrice: Double,
    val userId: String
) : Serializable