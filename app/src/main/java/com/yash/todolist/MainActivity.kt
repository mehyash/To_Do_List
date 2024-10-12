package com.yash.todolist

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import android.media.MediaPlayer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yash.todolist.databinding.ActivityMainBinding
import com.yash.todolist.databinding.CardBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var sharedPref="todo"
    lateinit var array:ArrayList<todotask>
    lateinit var adapter:todoadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        array= getbacklist()

        binding.addtask.setOnClickListener {
            var task=binding.task.text.toString()
            if(task.isNotEmpty()) {
                array.add(todotask(task))
                adapter.notifyDataSetChanged()
            }
        }
        adapter=todoadapter(array)
        binding.rv.layoutManager=LinearLayoutManager(this)
        binding.rv.adapter=adapter
        val swipedelete=object : swipetodelete(){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                array.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this@MainActivity,"Item Removed Successfully",Toast.LENGTH_SHORT).show()
            }

        }
        val itemTouchHelper=ItemTouchHelper(swipedelete)
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }
    fun getbacklist(): ArrayList<todotask> {
        val sharedPref: SharedPreferences = this.getSharedPreferences(sharedPref,MODE_PRIVATE)
        val gson=Gson()
        val json=sharedPref.getString("jsonarray",null)
        if(json!=null){
            val type=object :TypeToken<ArrayList<todotask>>(){}.type
            return  gson.fromJson(json,type)
        }
        return ArrayList()
    }
    override fun onStop() {
        super.onStop()
        var sharedPref:SharedPreferences=this.getSharedPreferences(sharedPref, MODE_PRIVATE)
        var editor: SharedPreferences.Editor=sharedPref.edit()
        val gson=Gson()
        val json:String=gson.toJson(array)
        editor.putString("jsonarray",json)
        editor.apply()
        editor.commit()
    }


}