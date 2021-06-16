package com.madlab.medicinedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.madlab.medicinedb.db.MedicineDatabase
import com.madlab.medicinedb.db.MedicineModel
import kotlinx.coroutines.launch

class MedicineViewModel : ViewModel(){

    private val mMedicineList = MutableLiveData<List<MedicineModel>>()
    val medicineList = mMedicineList as LiveData<List<MedicineModel>>

    lateinit var db : MedicineDatabase

    fun load(){
        if(this::db.isInitialized) {
            viewModelScope.launch {
                db.withTransaction {
                    mMedicineList.postValue(db.dao().getList())
                }
            }
        }
    }
}
