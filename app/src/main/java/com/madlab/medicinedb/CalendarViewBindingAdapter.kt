package com.madlab.medicinedb

import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener

import androidx.databinding.InverseBindingListener

import androidx.databinding.BindingAdapter
import java.util.*
import androidx.databinding.InverseBindingAdapter
import android.widget.TextView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.madlab.medicinedb.db.Day


object CalendarViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = ["android:onSelectedDayChange", "android:dateAttrChanged"],
        requireAll = false
    )
    fun setListeners(
        view: CalendarView,
        onDayChange: OnDateChangeListener?,
        attrChange: InverseBindingListener?
    ) {
        if (attrChange == null) {
            view.setOnDateChangeListener(onDayChange)
        } else {
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDayChange?.onSelectedDayChange(view, year, month, dayOfMonth)
                val instance: Calendar = Calendar.getInstance()
                instance.set(year, month, dayOfMonth)
                view.date = instance.timeInMillis
                attrChange.onChange()
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:dateLong", event = "android:dateAttrChanged")
    fun getDateLong(view: CalendarView): Long {
        return view.date
    }

    @JvmStatic
    @BindingAdapter("android:dateLong")
    fun setDate(view: CalendarView, date: Long) {
        if (view.date != date) {
            view.date = date
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun convertStringToValidatedField(view: TextInputEditText): String {
        return view.text.toString()
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun convertStringToValidatedField(view: MaterialAutoCompleteTextView): Day {
        return Day.valueOf(view.text.toString())
    }

}