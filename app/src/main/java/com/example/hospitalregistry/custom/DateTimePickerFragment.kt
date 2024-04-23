package com.example.hospitalregistry.custom

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DateTimePickerFragment : DialogFragment() {

    private var onDateTimeSetListener: OnDateTimeSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Создание диалога выбора даты
        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    onDateTimeSetListener?.onDateTimeSet(selectedYear, selectedMonth, selectedDay, hourOfDay, minute)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.setTitle("Select Time")
            timePickerDialog.show()
        }, year, month, day)

        // Настройка минимального и максимального значения даты
        val minDate = Calendar.getInstance()
        datePickerDialog.datePicker.minDate = minDate.timeInMillis
        minDate.add(Calendar.MONTH, 1) // Например, минимальная дата - текущая дата, максимальная - через месяц
        datePickerDialog.datePicker.maxDate = minDate.timeInMillis

        return datePickerDialog
    }

    fun setOnDateTimeSetListener(listener: OnDateTimeSetListener) {
        onDateTimeSetListener = listener
    }

    interface OnDateTimeSetListener {
        fun onDateTimeSet(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int)
    }
}
