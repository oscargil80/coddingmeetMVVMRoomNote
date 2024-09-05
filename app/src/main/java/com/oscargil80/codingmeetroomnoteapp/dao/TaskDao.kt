package com.oscargil80.codingmeetroomnoteapp.dao

import android.util.Log
import androidx.room.*
import com.oscargil80.codingmeetroomnoteapp.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.*

@Dao
interface TaskDao {


    @Query("""SELECT * FROM Task ORDER BY
        CASE WHEN :isAsc = 1 THEN taskTittle END ASC, 
        CASE WHEN :isAsc = 0 THEN taskTittle END DESC""")
    fun getTaskListSortByTaskTitle(isAsc: Boolean) : Flow<List<Task>>

    @Query("""SELECT * FROM  Task Order by  
        CASE WHEN :isAsc = 1 THEN date END ASC,
        CASE WHEN :isAsc = 0 THEN date END DESC  """)
    fun getTaskListSortByTaskDate(isAsc: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Delete
    suspend fun deleteTask(task: Task): Int

    @Query("Delete from task where taskId == :taskID")
    suspend fun deleteTaskUsingId(taskID: String): Int

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("update task set taskTittle= :title, description= :description  where taskId == :taskID ")
    suspend fun updateTaskParticularField(taskID: String, title: String, description: String): Int

    @Query("SELECT * FROM  Task where taskTittle like :query Order by  date DESC")
    fun searchTaskList(query: String): Flow<List<Task>>


}