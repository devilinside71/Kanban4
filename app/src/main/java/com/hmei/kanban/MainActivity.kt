package com.hmei.kanban

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hmei.kanban.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CustomListener {

    private lateinit var binding: ActivityMainBinding
    val dummy=Dummy()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        //binding.recyclerViewTodo.init(listOf("A", "B", "C"), binding.emptyListTextView1)
        var arrayListTodo=dummy.dummyListTodo()
        var listTodo: List<String> = emptyList()
        arrayListTodo.forEach{
            listTodo+=it.description
        }
        var arrayListInProgress=dummy.dummyListInProgress()
        var listInProgress: List<String> = emptyList()
        arrayListInProgress.forEach{
            listInProgress+=it.description
        }
        var arrayListDone=dummy.dummyListDone()
        var listDone: List<String> = emptyList()
        arrayListDone.forEach{
            listDone+=it.description
        }


        binding.recyclerViewTodo.init(listTodo, binding.emptyListTextView1)
        binding.recyclerViewInProgress.init(listInProgress, binding.emptyListTextView2)
        binding.recyclerViewDone.init(listDone, binding.emptyListTextView3)

        fabAdd.setOnClickListener {
            Log.e("Add","add item")
            //Log.e("List", dummy.dummyString())
        }

    }

    private fun RecyclerView.init(list: List<String>, emptyTextView: TextView) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CustomAdapter(list, this@MainActivity)
        this.adapter = adapter
        emptyTextView.setOnDragListener(adapter.dragInstance)
        this.setOnDragListener(adapter.dragInstance)
    }

    override fun setEmptyList(visibility: Int, recyclerView: Int, emptyTextView: Int) {
        findViewById<RecyclerView>(recyclerView).visibility = visibility
        findViewById<TextView>(emptyTextView).visibility = visibility
    }
}

