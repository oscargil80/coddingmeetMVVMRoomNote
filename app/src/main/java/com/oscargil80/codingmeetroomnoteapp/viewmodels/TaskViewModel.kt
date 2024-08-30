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


}