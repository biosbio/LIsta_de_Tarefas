package com.jgonzalez.to_do_list_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jgonzalez.to_do_list_app.model.Task
import com.jgonzalez.to_do_list_app.viewmodel.TaskViewModel
import java.util.Calendar

class TarefasAdd : AppCompatActivity() {

    val taskTitle : EditText by lazy { findViewById(R.id.et_title) }
    val taskDate : EditText by lazy { findViewById(R.id.et_date) }
    val taskTime : EditText by lazy { findViewById(R.id.et_time) }
    val btnCancel :  Button by lazy { findViewById(R.id.cancelar) }
    val btnSave : Button by lazy { findViewById(R.id.btn_save) }

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_tarefas)



        setupButtons()
        setupCalendar()
        setupClock()

    }


    private fun setupButtons() {
        btnSave.setOnClickListener {
            val newTask = Task(title = taskTitle.text.toString(), data = taskDate.text.toString(), time = taskTime.text.toString())
            taskViewModel.insert(newTask)

            Intent(this, MainActivity::class.java).also{
                startActivity(it)
            }

            Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show()
        }


        btnCancel.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Do you want to cancel")
                .setPositiveButton("Confirm") {dialog,_ ->
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") {dialog,_ ->

                    dialog.dismiss()

                }
                .create()

            alertDialog.show()
        }

    }

private fun setupClock() {
    taskTime.setOnClickListener {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->

            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            taskTime.setText(formattedTime)

        }, hour, minute, true).show()
    }
}

private fun setupCalendar() {
    taskDate.setOnClickListener {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            val formattedDate = String.format("%02d/%02d/%04d", selectedDay , selectedMonth + 1, selectedYear)
            taskDate.setText(formattedDate)

            }, year, month, day).show()
        }
    }

}