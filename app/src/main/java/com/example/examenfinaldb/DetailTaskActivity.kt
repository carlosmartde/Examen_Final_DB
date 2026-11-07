package com.example.examenfinaldb

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class DetailTaskActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DESCRIPTION = "extra_detail_description"
        const val EXTRA_COMPLETED = "extra_detail_completed"
        const val EXTRA_CREATED_AT = "extra_detail_created_at"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        supportActionBar?.apply {
            title = getString(R.string.detail_task_title)
            setDisplayHomeAsUpEnabled(true)
        }

        val description = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()
        val completed = intent.getBooleanExtra(EXTRA_COMPLETED, false)
        val createdAt = intent.getStringExtra(EXTRA_CREATED_AT).orEmpty()

        findViewById<TextView>(R.id.detailDescription).text = description
        findViewById<TextView>(R.id.detailCreatedAt).text = createdAt
        findViewById<TextView>(R.id.detailStatus).apply {
            text = getString(if (completed) R.string.status_completed else R.string.status_pending)
            setTextColor(
                ContextCompat.getColor(
                    this@DetailTaskActivity,
                    if (completed) R.color.status_completed else R.color.status_pending
                )
            )
        }

        findViewById<MaterialButton>(R.id.backToListButton).setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
