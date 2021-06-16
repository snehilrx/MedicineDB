package com.madlab.medicinedb

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.madlab.medicinedb.databinding.AddFragmentBinding
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.madlab.medicinedb.db.Day
import com.madlab.medicinedb.db.MedicineModel
import java.lang.Exception
import android.provider.CalendarContract
import android.widget.ArrayAdapter
import java.util.*


class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var binding: AddFragmentBinding

    private var mId: String? = null
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        viewModel.db = (activity as MainActivity).db
        mId = arguments?.getString("MID")
        if(!mId.equals("null")){
            viewModel.load(mId!!)
        }
        binding.lifecycleOwner = this
        binding.med = viewModel
        binding.save.setOnClickListener {
                val med = viewModel.save()
                createAlarm(med)
                mAct.gotoHome()
        }
        mAct.fabVisibility(View.GONE)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.days))
        binding.medicineTodS.setAdapter(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun createAlarm(med: MedicineModel){
        try {
            Toast.makeText(requireContext(), "Adding Event To Your Calendar...", Toast.LENGTH_SHORT).show();
            val event = ContentValues()
            val startcalendar: Calendar = Calendar.getInstance()
            val endcalendar: Calendar = Calendar.getInstance()
            startcalendar.timeInMillis = med.date
            when(med.timeOfDay){
                Day.Morning -> {
                    startcalendar.set(Calendar.HOUR_OF_DAY, 8)
                    endcalendar.set(Calendar.HOUR_OF_DAY, 9)
                }
                Day.Evening -> {
                    startcalendar.set(Calendar.HOUR_OF_DAY, 17)
                    endcalendar.set(Calendar.HOUR_OF_DAY, 18)
                }
                Day.Night -> {
                    startcalendar.set(Calendar.HOUR_OF_DAY, 20)
                    endcalendar.set(Calendar.HOUR_OF_DAY, 21)
                }
                Day.Afternoon -> {
                    startcalendar.set(Calendar.HOUR_OF_DAY, 12)
                    endcalendar.set(Calendar.HOUR_OF_DAY, 13)
                }
            }
            event.put(CalendarContract.Events.CALENDAR_ID, 1)
            event.put(CalendarContract.Events.TITLE, med.name)
            event.put(CalendarContract.Events.DESCRIPTION, "Medicine Reminder")
            event.put(CalendarContract.Events.DTSTART, startcalendar.timeInMillis)
            event.put(CalendarContract.Events.DTEND, endcalendar.timeInMillis)
            event.put(CalendarContract.Events.HAS_ALARM, true)
            event.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT-05:00")

            val contentResolver = activity?.applicationContext?.contentResolver
            val url: Uri? = contentResolver?.insert(CalendarContract.Events.CONTENT_URI, event)

            val eventId = url?.lastPathSegment?.toLong()

            val reminder = ContentValues()
            reminder.put(CalendarContract.Reminders.EVENT_ID, eventId)
            reminder.put(CalendarContract.Reminders.MINUTES, 10)
            reminder.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            contentResolver?.insert(CalendarContract.Reminders.CONTENT_URI, reminder)

        } catch (ex: Exception) {
            Log.e(".med","Error in adding event on calendar" + ex.message)
        }
    }

    val mAct by lazy {activity as MainActivity}
}