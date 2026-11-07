package com.example.examenfinaldb

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskStorage(context: Context) {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun loadTasks(): MutableList<Task> {
        val savedTasks = preferences.getString(KEY_TASKS, null)
            ?: return createInitialTasks()

        return runCatching {
            val jsonArray = JSONArray(savedTasks)
            MutableList(jsonArray.length()) { index ->
                val jsonTask = jsonArray.getJSONObject(index)
                Task(
                    description = jsonTask.getString(KEY_DESCRIPTION),
                    completed = jsonTask.getBoolean(KEY_COMPLETED),
                    createdAt = jsonTask.getString(KEY_CREATED_AT)
                )
            }
        }.getOrDefault(mutableListOf())
    }

    fun saveTasks(tasks: List<Task>) {
        val jsonArray = JSONArray()
        tasks.forEach { task ->
            jsonArray.put(
                JSONObject().apply {
                    put(KEY_DESCRIPTION, task.description)
                    put(KEY_COMPLETED, task.completed)
                    put(KEY_CREATED_AT, task.createdAt)
                }
            )
        }
        preferences.edit().putString(KEY_TASKS, jsonArray.toString()).apply()
    }

    private fun createInitialTasks(): MutableList<Task> {
        val createdAt = currentDate()
        return mutableListOf(
            Task("Preparar informe diario", false, createdAt),
            Task("Revisar inventario", true, createdAt),
            Task("Responder correos pendientes", false, createdAt)
        ).also(::saveTasks)
    }

    private fun currentDate(): String =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

    companion object {
        private const val PREFERENCES_NAME = "task_preferences"
        private const val KEY_TASKS = "tasks"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_COMPLETED = "completed"
        private const val KEY_CREATED_AT = "created_at"
    }
}
