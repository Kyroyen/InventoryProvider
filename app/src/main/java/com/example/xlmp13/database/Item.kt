package com.example.xlmp13.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "price") val price:Double,
    @ColumnInfo(name = "quantity") val quantity:Int,
){
    fun getPriceString(): String = "$${price}"

    fun getQuantityString(): String = quantity.toString()

}
