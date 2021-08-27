package com.example.souqadmin.pojo

import java.io.Serializable

class ProductResponse : ArrayList<ProductResponse.Item>(){


    data class Item(
            val brand: String,
            val category: Category,
            var color: List<String>,
            val companyName: String,
            val description: String,
            val id: Int,
            val image: String,
            val price: Double,
            var quantity: Int,
            val rating: Float,
            val sale: Sale,
            val size: List<String>,
            val title: String


    ) : Serializable{
        data class Category(
            val name: String,
            val url: String
        )
        data class Sale(
            val amount: Int,
            val duration: String,
            val image: List<String>?,
            val type: String
        )
    }
}