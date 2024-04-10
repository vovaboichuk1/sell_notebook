package com.cricoo.sell_notebook

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class EditProfile : DialogFragment() {

    private lateinit var listener: EditProfileDialogListener
    private lateinit var field: String
    private lateinit var value: String
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private val calendar = Calendar.getInstance()

    private lateinit var textViewDOB: EditText

    interface EditProfileDialogListener {
        fun onDialogPositiveClick(field: String, value: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            field = it.getString(ARG_FIELD, "")
            value = it.getString(ARG_VALUE, "")
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
            textViewDOB.setText(selectedDate)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as EditProfileDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement EditProfileDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        textViewDOB = EditText(activity)
        textViewDOB.setText(value)
        textViewDOB.setOnClickListener {
            showDatePickerDialog()
        }

        return AlertDialog.Builder(requireActivity())
            .setTitle("Редагування $field")
            .setView(textViewDOB)
            .setPositiveButton("Зберегти") { _, _ ->
                listener.onDialogPositiveClick(field, textViewDOB.text.toString())
            }
            .setNegativeButton("Скасувати") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    companion object {
        private const val ARG_FIELD = "field"
        private const val ARG_VALUE = "value"

        fun newInstance(field: String, value: String): EditProfile {
            val fragment = EditProfile()
            val args = Bundle()
            args.putString(ARG_FIELD, field)
            args.putString(ARG_VALUE, value)
            fragment.arguments = args
            return fragment
        }
    }
}
