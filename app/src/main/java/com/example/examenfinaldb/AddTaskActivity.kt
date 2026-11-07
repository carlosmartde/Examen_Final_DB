package com.example.examenfinaldb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DESCRIPTION = "extra_task_description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.apply {
            title = getString(R.string.add_task_title)
            setDisplayHomeAsUpEnabled(true)
        }

        val inputLayout = findViewById<TextInputLayout>(R.id.descriptionInputLayout)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descriptionEditText)

        findViewById<MaterialButton>(R.id.saveTaskButton).setOnClickListener {
            val description = descriptionInput.text?.toString()?.trim().orEmpty()
            if (description.isBlank()) {
                inputLayout.error = getString(R.string.description_required)
            } else {
                inputLayout.error = null
                setResult(
                    Activity.RESULT_OK,
                    Intent().putExtra(EXTRA_DESCRIPTION, description)
                )
                finish()
            }
        }

        findViewById<MaterialButton>(R.id.cancelTaskButton).setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
