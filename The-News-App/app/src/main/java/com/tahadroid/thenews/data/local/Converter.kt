package com.tahadroid.thenews.data.local

import androidx.room.TypeConverter
import com.tahadroid.thenews.models.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}