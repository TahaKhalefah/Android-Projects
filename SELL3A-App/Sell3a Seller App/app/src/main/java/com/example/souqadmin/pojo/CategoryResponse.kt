package com.example.souqadmin.pojo
class CategoryResponse : ArrayList<CategoryResponse.CategoryResponseItem>(){
    data class CategoryResponseItem(
        val name: String,
        val url: String
    )
}