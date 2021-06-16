package com.madlab.medicinedb

import androidx.databinding.Bindable
import androidx.lifecycle.*
import androidx.room.withTransaction
import com.madlab.medicinedb.db.Day
import com.madlab.medicinedb.db.MedicineDao
import com.madlab.medicinedb.db.MedicineDatabase
import com.madlab.medicinedb.db.MedicineModel
import kotlinx.coroutines.launch
import java.util.*
import androidx.databinding.ObservableField




class AddViewModel : ViewModel() {

    lateinit var db : MedicineDatabase

    var medicineModel = MedicineModel()

    fun load(mId: String) {
        if(this::db.isInitialized){
            viewModelScope.launch {
                db.withTransaction {
                    medicineModel = db.dao().getMedicine(mId)
                }
            }
        }
    }

    fun save() : MedicineModel{
        viewModelScope.launch {
            db.withTransaction {
                db.dao().insertMedicine(medicineModel)
            }
        }
        return medicineModel
    }
}