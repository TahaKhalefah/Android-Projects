package com.tahadroid.wing.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tahadroid.wing.models.Order
import com.tahadroid.wing.utils.Constants.DATABASE_NAME


@Database(
    entities = [Order::class],
    version = 4
)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun getOrderDoa(): OrderDoa

    companion object {
        @Volatile
        private var instance: OrderDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                OrderDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}