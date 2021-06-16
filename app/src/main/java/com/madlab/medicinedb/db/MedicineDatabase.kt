package com.madlab.medicinedb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        MedicineModel::class
    ],
    version = 3,
    exportSchema = false
)
abstract class MedicineDatabase : RoomDatabase() {

    abstract fun dao(): MedicineDao

}