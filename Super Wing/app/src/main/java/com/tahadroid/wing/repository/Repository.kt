package com.tahadroid.wing.repository

import com.tahadroid.wing.data.local.OrderDatabase
import com.tahadroid.wing.models.Order

class Repository(val db: OrderDatabase) {

    suspend fun upsertOrder(order: Order) = db.getOrderDoa().upsertOrder(order)
    suspend fun deleteOrder(order: Order) = db.getOrderDoa().deleteOrder(order)
    fun getAllOrders() = db.getOrderDoa().getAllOrders()
    fun deleteAll()=db.getOrderDoa().deleteAllOrders()
}