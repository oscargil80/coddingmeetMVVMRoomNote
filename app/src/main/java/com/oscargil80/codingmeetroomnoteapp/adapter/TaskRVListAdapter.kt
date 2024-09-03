package com.oscargil80.codingmeetroomnoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.codingmeetroomnoteapp.R
import com.oscargil80.codingmeetroomnoteapp.databinding.ViewTaskLayoutBinding
import com.oscargil80.codingmeetroomnoteapp.models.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskRVListAdapter(
    private val deleteUpdateCallback: (type:String, position: Int, task: Task) -> Unit,
) :
    ListAdapter<Task, TaskRVListAdapter.ViewHolder>(DiffCallBack()) {

   // private val taskList = arrayListOf<Task>()


    class ViewHolder( val viewTaskLayoutBinding: ViewTaskLayoutBinding)
        : RecyclerView.ViewHolder(viewTaskLayoutBinding.root)


/*    fun addAllTask(newTaskList: List<Task>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task =getItem(position)

        holder.viewTaskLayoutBinding.titleTxt.text = task.tittle
        holder.viewTaskLayoutBinding.descrTxt.text = task.description

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.viewTaskLayoutBinding.dateTxt.text = dateFormat.format(task.date)

        holder.viewTaskLayoutBinding.deleteImg.setOnClickListener {
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("delete", holder.adapterPosition, task)
            }
        }

        holder.viewTaskLayoutBinding.editImg.setOnClickListener {
            if(holder.adapterPosition != -1){
                deleteUpdateCallback("update", holder.adapterPosition, task)
            }
        }

    }



    class DiffCallBack : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return    oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return  oldItem == newItem
        }

    }


}