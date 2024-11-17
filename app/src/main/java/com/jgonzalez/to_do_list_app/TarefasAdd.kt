package com.jgonzalez.to_do_list_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jgonzalez.to_do_list_app.model.Task
import com.jgonzalez.to_do_list_app.viewmodel.TaskViewModel

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


    }
}