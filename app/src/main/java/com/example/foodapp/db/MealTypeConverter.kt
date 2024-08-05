package com.example.foodapp.db

import androidx.resourceinspection.annotation.Attribute
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    fun fromAntToString(attribute: Any?):String{
        if(attribute==null)
            return ""
        return attribute as String
    }
    @TypeConverters
    fun fromStringToAny(attribute: String?):Any{
        if(attribute==null)
            return ""
        return attribute

    }
}