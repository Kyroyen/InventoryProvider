package com.example.xlmp13.database

import android.app.Application

class InventoryApplication : Application() {
    val database: ItemRoomDatabase by lazy {
        ItemRoomDatabase.getDatabase(this)
    }
}