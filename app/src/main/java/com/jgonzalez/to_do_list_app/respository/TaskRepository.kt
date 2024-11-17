package com.jgonzalez.to_do_list_app.respository

import androidx.lifecycle.LiveData
import com.jgonzalez.to_do_list_app.database.TaskDao
import com.jgonzalez.to_do_list_app.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val allTask: LiveData<List<Task>> = taskDao.getAllTasks()


    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}