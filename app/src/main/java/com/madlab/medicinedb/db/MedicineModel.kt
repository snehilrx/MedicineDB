package com.madlab.medicinedb.db

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madlab.medicinedb.BR
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "medicine"
)
data class MedicineModel (
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var date: Long = System.currentTimeMillis(),
    var timeOfDay: Day = Day.Morning) : BaseObservable() {

    @Bindable
    fun getIdB() = id

    @Bindable
    fun setIdB(id: String) {
        this.id = id;
        notifyPropertyChanged(BR.med)
    }

    @Bindable
    fun getNameB() = name

    @Bindable
    fun setNameB(name: String) {
        this.name = name;
        notifyPropertyChanged(BR.med)
    }

    @Bindable
    fun getDateB() = date

    @Bindable
    fun setDateB(date: Long) {
        this.date = date;
        notifyPropertyChanged(BR.med)
    }

    var temp = Day.Morning.name

    @Bindable
    fun getTodB() = temp

    @Bindable
    fun setTodB(name: String) {
        try {
            this.timeOfDay = Day.valueOf(name)
        }catch (e: Exception) {
            // ignored
        }
        temp = name
        notifyPropertyChanged(BR.med)
    }

    object Utils{
        @JvmStatic
        fun getDate(dateL: Long) : String{
            val date = Date(dateL)
            val df2 = SimpleDateFormat.getDateInstance()
            return  df2.format(date)
        }
    }

}

enum class Day {
    Morning,
    Evening,
    Night,
    Afternoon
}