package com.oscargil80.codingmeetroomnoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.codingmeetroomnoteapp.R
import com.oscargil80.codingmeetroomnoteapp.databinding.ViewTaskGripLayoutBinding

import com.oscargil80.codingmeetroomnoteapp.databinding.ViewTaskListLayoutBinding
import com.oscargil80.codingmeetroomnoteapp.models.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskRVBindingAdapter(
    private val isList: MutableLiveData<Boolean>,
    private val deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val taskList = arrayListOf<Task>()


    class ListTaskViewHolder(private val viewTaskListLayoutBinding: ViewTaskListLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskListLayoutBinding.root) {
        fun bind(
            task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {

            viewTaskListLayoutBinding.titleTxt.text = task.tittle
            viewTaskListLayoutBinding.descrTxt.text = task.description

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
            viewTaskListLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            viewTaskListLayoutBinding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }

            viewTaskListLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }

        }
    }

    class GripTaskViewHolder(private val viewTaskGripLayoutBinding: ViewTaskGripLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskGripLayoutBinding.root) {
        fun bind(
            task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {

            viewTaskGripLayoutBinding.titleTxt.text = task.tittle
            viewTaskGripLayoutBinding.descrTxt.text = task.description

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
            viewTaskGripLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            viewTaskGripLayoutBinding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }

            viewTaskGripLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
        GripTaskViewHolder(
            ViewTaskGripLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }else{
            ListTaskViewHolder(
                ViewTaskListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = taskList[position]

        if (isList.value!!){
            (holder as ListTaskViewHolder).bind(task, deleteUpdateCallback)
        }else{
            (holder as GripTaskViewHolder).bind(task, deleteUpdateCallback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isList.value!!){
0//List_Item
        }else{
            1//Grip_Item
        }
    }

    override fun getItemCount() = taskList.size


}