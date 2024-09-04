package com.oscargil80.codingmeetroomnoteapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.oscargil80.codingmeetroomnoteapp.adapter.TaskRVListAdapter
import com.oscargil80.codingmeetroomnoteapp.adapter.TaskRecyclerViewAdapter
import com.oscargil80.codingmeetroomnoteapp.databinding.ActivityMainBinding
import com.oscargil80.codingmeetroomnoteapp.models.Task
import com.oscargil80.codingmeetroomnoteapp.util.*
import com.oscargil80.codingmeetroomnoteapp.util.StatusResult.*
import com.oscargil80.codingmeetroomnoteapp.viewmodels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val addTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.add_task_dialog)
        }
    }

    private val updateTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.update_task_dialog)
        }
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.loading_dialog)
        }
    }

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this).get(TaskViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)


        // Add task start

        val addCloseImg = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        addCloseImg.setOnClickListener {
            addTaskDialog.dismiss()
        }

        val addETTitle = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val addETTitleL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        addETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETTitle, addETTitleL)
            }
        })

        val addETDesc = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val addETDescL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        addETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETDesc, addETDescL)
            }
        })

        mainBinding.addTaskFABtn.setOnClickListener {
            clearEditText(addETTitle, addETTitleL)
            clearEditText(addETDesc, addETDescL)
            addTaskDialog.show()
        }
        val saveTaskBtn = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveTaskBtn.setOnClickListener {
            if (validateEditText(addETTitle, addETTitleL)
                && validateEditText(addETDesc, addETDescL)
            ) {

                val newTask = Task(
                    UUID.randomUUID().toString(),
                    addETTitle.text.toString().trim(),
                    addETDesc.text.toString().trim(),
                    Date()
                )
                hideKeyBoard(it)
                addTaskDialog.dismiss()
                taskViewModel.insertTask(newTask)

            }
        }
        // Add task end


        //update task Start
        val updateETTitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val updateETTitleL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        updateETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETTitle, updateETTitleL)
            }
        })

        val updateETDesc = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val updateETDescL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        updateETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETDesc, updateETDescL)
            }
        })

        val updateCloseImg = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)
        updateCloseImg.setOnClickListener {
            updateTaskDialog.dismiss()
        }

        val updateTaskBtn = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)

        //update task End

        val taskRVListAdapter = TaskRVListAdapter { type, position, task ->
            if (type == "delete") {
                taskViewModel
                        //Deleted task
                    //.deleteTask(task)
                    .deleteTaskUsingId(task.id)

                //Restore deleted task
                restoreDeletedTask(task)
            } else if (type == "update") {
                updateETTitle.setText(task.tittle)
                updateETDesc.setText(task.description)

                updateTaskBtn.setOnClickListener {
                    if (validateEditText(updateETTitle, updateETTitleL)
                        && validateEditText(updateETDesc, updateETDescL)
                    ) {
                        val updateTask = Task(
                            task.id,
                            updateETTitle.text.toString().trim(),
                            updateETDesc.text.toString().trim(),
                            Date()
                        )
                        hideKeyBoard(it)
                        updateTaskDialog.dismiss()
                        taskViewModel
                            .updateTask(updateTask)
                        /*    .updateTaskParticularField(
                                task.id,
                                updateETTitle.text.toString().trim(),
                                updateETDesc.text.toString().trim(),
                            )*/
                    }
                }
                updateTaskDialog.show()
            }
        }
        mainBinding.taskRV.adapter = taskRVListAdapter
        taskRVListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                mainBinding.taskRV.smoothScrollToPosition(positionStart)
            }
        }
        )
        callGetTaskList(taskRVListAdapter)
        taskViewModel.getTaskList()
        statusCallBack()
        callSearch()
    }

    private fun restoreDeletedTask( deletedTask: Task){
        val snarkBar = Snackbar.make(
            mainBinding.root, "Deleted ${deletedTask.tittle}",
            Snackbar.LENGTH_INDEFINITE
        )
        snarkBar.setAction("Deshacer Eliminado ?"){
            taskViewModel.insertTask(deletedTask)
        }
        snarkBar.show()
    }

    private fun callSearch() {
        mainBinding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(query: Editable) {
                if (query.toString().isNotEmpty()) {
                    taskViewModel.searchTaskList(query.toString())
                } else {
                    taskViewModel.getTaskList()
                }
            }

        })
        mainBinding.edSearch.setOnEditorActionListener{v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                hideKeyBoard(v)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun statusCallBack() {
        taskViewModel
            .statusLiveData
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        when (it.date as StatusResult) {
                            Added -> {
                                Log.d("StatusResult", "Added")
                            }
                            Deleted -> {
                                Log.d("StatusResult", "Deleted")
                            }
                            Updated -> {
                                Log.d("StatusResult", "Updated")
                            }
                        }
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 ->
                            longToastShow(it1)
                        }
                    }
                }
            }
    }

    private fun callGetTaskList(taskRecyclerViewAdapter: TaskRVListAdapter) {


        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.taskStateFlow
                .collectLatest {
                    when (it.status) {
                        Status.LOADING -> {
                            loadingDialog.show()
                        }
                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            it.date?.collect { taskList ->

                                taskRecyclerViewAdapter.submitList(taskList)
                                Log.e("Paso", "PASO POR AQUI2")
                            }

                        }
                        Status.ERROR -> {
                            loadingDialog.dismiss()
                            it.message?.let { it1 ->
                                longToastShow(it1)
                            }
                        }
                    }
                }
        }
    }
}