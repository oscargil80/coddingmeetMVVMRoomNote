package com.oscargil80.codingmeetroomnoteapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.oscargil80.codingmeetroomnoteapp.models.Task
import com.oscargil80.codingmeetroomnoteapp.repository.TaskRepository
import com.oscargil80.codingmeetroomnoteapp.util.Resource


class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)

    fun getTaskList() = taskRepository.getTaskList()

    fun insertTask(task: Task): MutableLiveData<Resource<Long>>{
        return  taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task): MutableLiveData<Resource<Int>>{
        return  taskRepository.deleteTask(task)
    }

    fun deleteTaskUsingId(taskId: String): MutableLiveData<Resource<Int>>{
        return  taskRepository.deleteTaskUsingId(taskId)
    }

    fun updateTaskParticularField(taskId: String, title:String, description:String): MutableLiveData<Resource<Int>>{
        return  taskRepository.updateTaskParticularField(taskId, title, description)
    }

    fun updateTask(task: Task): MutableLiveData<Resource<Int>>{
        return  taskRepository.updateTask(task)
    }

}