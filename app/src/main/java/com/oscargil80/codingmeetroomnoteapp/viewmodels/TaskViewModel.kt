package com.oscargil80.codingmeetroomnoteapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.oscargil80.codingmeetroomnoteapp.models.Task
import com.oscargil80.codingmeetroomnoteapp.repository.TaskRepository
import com.oscargil80.codingmeetroomnoteapp.util.Resource


class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)
    val taskStateFlow get() = taskRepository.taskStateFlow
    val statusLiveData get() = taskRepository.statusLiveData


    fun getTaskList() {
        taskRepository.getTaskList()
    }


    fun insertTask(task: Task){
         taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task){
         taskRepository.deleteTask(task)
    }

    fun deleteTaskUsingId(taskId: String){
         taskRepository.deleteTaskUsingId(taskId)
    }

    fun updateTaskParticularField(taskId: String, title:String, description:String){
         taskRepository.updateTaskParticularField(taskId, title, description)
    }

    fun updateTask(task: Task){
         taskRepository.updateTask(task)
    }

    fun searchTaskList(query:String){
        taskRepository.searchTaskList(query)
    }

}