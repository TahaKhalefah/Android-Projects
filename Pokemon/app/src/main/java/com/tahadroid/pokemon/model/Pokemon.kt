package com.tahadroid.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var url: String
) {

}