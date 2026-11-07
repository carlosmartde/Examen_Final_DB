package com.example.examenfinaldb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.main_title)

        val tasks = listOf(
            Task("Preparar informe diario", false),
            Task("Revisar inventario", true),
            Task("Responder correos pendientes", false)
        )

        findViewById<RecyclerView>(R.id.taskRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TaskAdapter(tasks)
        }
    }
}
