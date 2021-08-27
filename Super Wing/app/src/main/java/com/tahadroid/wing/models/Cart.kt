package com.tahadroid.wing.models

data class Cart(
    val subTotal: Double,
    val Total: Double,
    val time: String,
    val delivery: Double,
    val orders: List<Order>,
    val adress: String,
    val date: String,
    val tax:Double
)