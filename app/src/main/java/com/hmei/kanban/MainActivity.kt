package com.hmei.kanban

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hmei.kanban.databinding.ActivityMainBinding


internal interface OnOK {
    fun onTextEntered(text: String?)
}

class MainActivity : AppCompatActivity(), CustomListener {

    private lateinit var binding: ActivityMainBinding
    val dummy = Dummy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)


        dummy.setGlobalLists()

        //binding.recyclerViewTodo.init(listOf("A", "B", "C"), binding.emptyListTextView1)
        val arrayListTodo = Global.todoList
        var listTodo: List<String> = emptyList()
        arrayListTodo.forEach {
            listTodo += it.description
        }
        val arrayListInProgress = Global.inProgressList
        var listInProgress: List<String> = emptyList()
        arrayListInProgress.forEach {
            listInProgress += it.description
        }
        val arrayListDone = Global.doneList
        var listDone: List<String> = emptyList()
        arrayListDone.forEach {
            listDone += it.description
        }


        binding.recyclerViewTodo.init(listTodo, binding.emptyListTextView1)
        binding.recyclerViewInProgress.init(listInProgress, binding.emptyListTextView2)
        binding.recyclerViewDone.init(listDone, binding.emptyListTextView3)

        fabAdd.setOnClickListener {
            Log.e("Add", "add item")
            var tempList = emptyList<String>()
            Global.todoList.forEach {
                tempList += it.description
            }
            tempList+="NEW TODO"

            val rvTodo = findViewById<RecyclerView>(R.id.recycler_view_todo)
            val rvAdapterTodo = CustomAdapter(tempList, this)
            rvTodo.adapter = rvAdapterTodo

        }

        // action bar
        supportActionBar?.apply {
            setTitle(R.string.app_name)
            setSubtitle(R.string.app_subtitle)

            // documentation source developer.android.com

            // Set whether to include the application home affordance in the
            // action bar. Home is presented as either an activity icon or logo.
            setDisplayShowHomeEnabled(true)


            // Set whether to display the activity logo rather than the
            // activity icon. A logo is often a wider, more detailed image.
            setDisplayUseLogoEnabled(true)


            // Set the logo to display in the 'home' section of the action bar.
            setLogo(R.mipmap.ic_launcher)
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

