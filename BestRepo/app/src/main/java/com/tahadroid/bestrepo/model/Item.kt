package com.tahadroid.bestrepo.model

data class Item(
    val owner: Owner,
    val stargazers_count: Int,
    val description: String ,
    val name: String,
)