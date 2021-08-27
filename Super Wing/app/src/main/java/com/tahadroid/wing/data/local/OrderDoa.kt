package com.tahadroid.wing.data.local

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.room.*
import com.tahadroid.wing.models.Order

@Dao
interface OrderDoa {
    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    suspend fun upsertOrder(order: Order): Long
    @Update
    fun updateOrder(order: Order)
    @Delete
    suspend fun deleteOrder(order: Order)
    @Query("SELECT * FROM orders_table")
    fun getAllOrders(): LiveData<List<Order>>
    @Query("DELETE  FROM orders_table")
    fun deleteAllOrders()
}