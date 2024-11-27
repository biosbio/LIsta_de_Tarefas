package com.jgonzalez.to_do_list_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jgonzalez.to_do_list_app.model.Task
import com.jgonzalez.to_do_list_app.viewmodel.TaskViewModel
import java.util.Calendar

class TarefasEdit : AppCompatActivity() {

    val taskTitle: EditText by lazy { findViewById(R.id.et_title) }
    val taskDate: EditText by lazy { findViewById(R.id.et_date) }
    val taskTime: EditText by lazy { findViewById(R.id.et_time) }
    val btnCancel :  Button by lazy { findViewById(R.id.cancelar) }
    val btnSave: Button by lazy { findViewById(R.id.btn_save) }
    val taskViewModel: TaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_tarefas)

        val extraId = intent.getIntExtra("EXTRA_ID", 0)
        val extraTitle = intent.getStringExtra("EXTRA_TITLE")
        val extraDate = intent.getStringExtra("EXTRA_DATE")
        val extraTime = intent.getStringExtra("EXTRA_TIME")
        val factoryExtraTitle = Editable.Factory.getInstance().newEditable(extraTitle)
        val factoryExtraDate = Editable.Factory.getInstance().newEditable(extraDate)
        val factoryExtraTime = Editable.Factory.getInstance().newEditable(extraTime)
        taskTitle.text = factoryExtraTitle
        taskDate.text = factoryExtraDate
        taskTime.text = factoryExtraTime


        setupButtons(extraId)
        setupCalendar()
        setupClock()
    }

    private fun setupClock() {
        taskTime.setOnClickListener {

            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->

                val formattedTime = String.format("%02d:%02d",  selectedHour, selectedMinute)
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

                val formattedDate = String.format("%02d/%02d/%04d",selectedDay, selectedMonth + 1, selectedYear)
                taskDate.setText(formattedDate)

            }, year, month, day).show()
        }
    }

    private fun setupButtons(taskId: Int) {
        btnSave.setOnClickListener {

            if (taskTitle.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_task_title), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (taskDate.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_task_date), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (taskTime.text.toString().isEmpty()) { 
                Toast.makeText(this, getString(R.string.empty_task_time), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (taskId != -1) {

                val updateTask = Task(
                    id = taskId,
                    title = taskTitle.text.toString(),
                    data = taskDate.text.toString(),
                    time = taskTime.text.toString()
                )
                taskViewModel.update(updateTask)


                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }

                Toast.makeText(this, getString(R.string.saved_sucess), Toast.LENGTH_SHORT).show()

            }
        }


        btnCancel.setOnClickListener {

             val alertDialog = AlertDialog.Builder(this)
                 .setTitle(getString(R.string.label_cancel))
                 .setMessage(getString(R.string.cancel_alert_dialog))
                 .setPositiveButton(getString(R.string.confirm_alert)) {dialog,_ ->
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }

                     dialog.dismiss()
                 }
                 .setNegativeButton(getString(R.string.cancel_alert)) {dialog,_ ->

                     dialog.dismiss()

                 }
                 .create()

            alertDialog.show()

        }
    }
}