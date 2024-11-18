package com.jgonzalez.to_do_list_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewStub
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgonzalez.to_do_list_app.adapter.TaskAdapter
import com.jgonzalez.to_do_list_app.databinding.ActivityMainBinding
import com.jgonzalez.to_do_list_app.model.Task
import com.jgonzalez.to_do_list_app.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    val include: ConstraintLayout by lazy { findViewById(R.id.include_emptyActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter(
            onEditTask = { task ->
                Intent(this, TarefasEdit::class.java).also {
                    it.putExtra("EXTRA_ID", task.id)
                    it.putExtra("EXTRA_TITLE", task.title)
                    startActivity(it)
                }
            },
            onDeleteTask = { task ->


                deleteTask(task)

            }
        )

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter

        taskViewModel.allTasks.observe(this, Observer { tasks ->
            if(!tasks.isNullOrEmpty()) {

                adapter.setTask(tasks)
                binding.recycleView.visibility = RecyclerView.VISIBLE
                include.visibility = ConstraintLayout.GONE

            } else {
                binding.recycleView.visibility = RecyclerView.GONE
                include.visibility = ConstraintLayout.VISIBLE
            }
        })



        binding.addFab.setOnClickListener {
            Intent(this, TarefasAdd::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun deleteTask(task: Task) {
        taskViewModel.delete(task)
        Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
    }
}