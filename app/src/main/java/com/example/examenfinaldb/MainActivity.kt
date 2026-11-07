package com.example.examenfinaldb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskStorage: TaskStorage

    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val description = result.data
                ?.getStringExtra(AddTaskActivity.EXTRA_DESCRIPTION)
                ?.trim()

            if (!description.isNullOrEmpty()) {
                taskAdapter.addTask(Task(description, false, currentDate()))
                taskRecyclerView.smoothScrollToPosition(tasks.lastIndex)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.main_title)

        taskStorage = TaskStorage(this)
        tasks.addAll(taskStorage.loadTasks())

        taskAdapter = TaskAdapter(
            tasks = tasks,
            onTaskClick = { task -> openTaskDetail(task) },
            onTasksChanged = { taskStorage.saveTasks(tasks) }
        )
        taskRecyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        findViewById<Button>(R.id.addTaskButton).setOnClickListener {
            addTaskLauncher.launch(Intent(this, AddTaskActivity::class.java))
        }
    }

    private fun openTaskDetail(task: Task) {
        startActivity(
            Intent(this, DetailTaskActivity::class.java).apply {
                putExtra(DetailTaskActivity.EXTRA_DESCRIPTION, task.description)
                putExtra(DetailTaskActivity.EXTRA_COMPLETED, task.completed)
                putExtra(DetailTaskActivity.EXTRA_CREATED_AT, task.createdAt)
            }
        )
    }

    private fun currentDate(): String =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
}
