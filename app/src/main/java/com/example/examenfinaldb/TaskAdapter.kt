package com.example.examenfinaldb

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.taskDescription)
        val status: TextView = itemView.findViewById(R.id.taskStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.description.text = task.description
        holder.status.text = holder.itemView.context.getString(
            if (task.completed) R.string.status_completed else R.string.status_pending
        )
        holder.status.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (task.completed) R.color.status_completed else R.color.status_pending
            )
        )
        holder.description.paintFlags = if (task.completed) {
            holder.description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.description.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int = tasks.size
}
