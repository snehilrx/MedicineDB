package com.madlab.medicinedb.db

import androidx.room.*

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicine(medicine: MedicineModel)

    @Query("select * from medicine")
    fun getList() : List<MedicineModel>

    @Query("delete from medicine")
    fun clearAll()

    @Delete
    fun deleteMedicine(medicine: MedicineModel)

    @Query("select * from medicine where id = :mId")
    fun getMedicine(mId: String): MedicineModel
}