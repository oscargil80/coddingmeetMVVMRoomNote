package com.oscargil80.codingmeetroomnoteapp.dao

import androidx.room.*
import com.oscargil80.codingmeetroomnoteapp.models.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM  Task Order by  date DESC")
    fun getTaskList() : Flow<List<Task>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long


    @Delete
    suspend fun deleteTask(task: Task):Int

    @Query("Delete from task where taskId == :taskID")
    suspend fun deleteTaskUsingId(taskID : String): Int

    @Update
    suspend fun updateTask(task: Task):Int

    @Query("update task set taskTittle= :title, description= :description  where taskId == :taskID ")
    suspend fun updateTaskParticularField(taskID: String, title: String, description:String):Int

    @Query("SELECT * FROM  Task where taskTittle like :query Order by  date DESC")
    fun searchTaskList(query:String) : Flow<List<Task>>


}