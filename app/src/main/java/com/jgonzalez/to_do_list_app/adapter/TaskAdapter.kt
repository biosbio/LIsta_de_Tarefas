package com.jgonzalez.to_do_list_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgonzalez.to_do_list_app.R
import com.jgonzalez.to_do_list_app.model.Task

class TaskAdapter(
    private val onEditTask: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
    ) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val taskTitle : TextView = itemView.findViewById(R.id.tv_task_title)
        val taskData : TextView  = itemView.findViewById(R.id.tv_task_date)
        val btnEdit  : ImageButton =itemView.findViewById(R.id.ib_edit)
        val btnDelete  : ImageButton =itemView.findViewById(R.id.ib_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tarefas, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.taskTitle.text = currentTask.title
        holder.taskData.text = currentTask.data
        holder.btnEdit.setOnClickListener {
            onEditTask(currentTask)
        }
        holder.btnDelete.setOnClickListener {
            onDeleteTask(currentTask)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setTask(tasks: List<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

}