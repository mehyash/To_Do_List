package com.yash.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class todoadapter(val array: ArrayList<todotask>) : RecyclerView.Adapter<todoadapter.todoviewHolder>() {

    class todoviewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val task = itemView.findViewById<TextView>(R.id.work)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return todoviewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: todoviewHolder, position: Int) {
        val current = array[position]
        holder.task.text = current.todo  // Assign the 'todo' property to the TextView
    }
}

