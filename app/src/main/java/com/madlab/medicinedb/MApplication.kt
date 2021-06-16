package com.madlab.medicinedb

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.madlab.medicinedb.db.MedicineDatabase

class MApplication : Application(){

    companion object{
        @JvmStatic
        lateinit var room : MedicineDatabase
    }

    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            MedicineDatabase::class.java,
            "medicine.db"
        ).build()
    }

    fun getDB() = room
}