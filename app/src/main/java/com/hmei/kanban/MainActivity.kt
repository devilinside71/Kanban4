package com.hmei.kanban

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hmei.kanban.TouchListener.ClickListener
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
        val rvTodo = findViewById<RecyclerView>(R.id.recycler_view_todo)
        val rvInProgress = findViewById<RecyclerView>(R.id.recycler_view_in_progress)
        val rvDone = findViewById<RecyclerView>(R.id.recycler_view_done)



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
            tempList += "NEW TODO"

            //val rvTodo = findViewById<RecyclerView>(R.id.recycler_view_todo)

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


        rvTodo.addOnItemTouchListener(
            TouchListener(
                this,
                rvTodo,
                object : ClickListener {
                    override fun onClick(view: View?, position: Int) {

                        // Write your code here
                        //Toast.makeText(this@MainActivity, "Its onClick! $position", Toast.LENGTH_SHORT).show()
                        //Log.e("TodoList","onClick $position")
                    }

                    override fun onLongClick(view: View?, position: Int) {

                        // Write your code here
                        //Toast.makeText(this@MainActivity, "Its onLongClick! $position", Toast.LENGTH_SHORT).show()
                        Log.e("TodoList", "onLongClick $position")
                        Log.e("SameList", Global.sameRecyclerView.toString())
                        Log.e("SamePosition", Global.samePosition.toString())
                    }

                    override fun onDoubleTap(view: View?, position: Int) {


                        // Write your code here
                        Log.e("TodoList", "onDoubleTap $position")
                    }
                })
        )
        rvInProgress.addOnItemTouchListener(
            TouchListener(
                this,
                rvTodo,
                object : ClickListener {
                    override fun onClick(view: View?, position: Int) {

                        // Write your code here
                    }

                    override fun onLongClick(view: View?, position: Int) {

                        // Write your code here
                        //Toast.makeText(this@MainActivity, "Its onLongClick! $position", Toast.LENGTH_SHORT).show()
//                        Log.e("InProgressList","onLongClick $position")
//                        Log.e("SameList",Global.sameRecyclerView.toString())
//                        Log.e("SamePosition",Global.samePosition.toString())
                        if (Global.sameRecyclerView && Global.samePosition) {
                            Log.i("List", "InProgress EditMode ON")
                            Log.i("Item", Global.inProgressList[position].description)
                            Global.inProgressList[position].description="BLABLA"
                            Log.i("ModList",Global.inProgressList.toString())
                            var tempList = emptyList<String>()
                            Global.inProgressList.forEach {
                                tempList += it.description
                            }
                            val rvInProgressAdapter = CustomAdapter(tempList, this@MainActivity)
//                            val rvAdapterInProgress = CustomAdapter(tempList, this)
                            rvInProgress.adapter = rvInProgressAdapter
                            rvInProgressAdapter.notifyDataSetChanged()
                        } else {
                            Log.i("List", "InProgress EditMode OFF")
                        }
                    }

                    override fun onDoubleTap(view: View?, position: Int) {

                        // Write your code here

                    }
                })
        )
        rvDone.addOnItemTouchListener(
            TouchListener(
                this,
                rvTodo,
                object : ClickListener {
                    override fun onClick(view: View?, position: Int) {

                        // Write your code here
                        //Toast.makeText(this@MainActivity, "Its onClick! $position", Toast.LENGTH_SHORT).show()
                        //Log.e("TodoList","onClick $position")
                    }

                    override fun onLongClick(view: View?, position: Int) {

                        // Write your code here
                        //Toast.makeText(this@MainActivity, "Its onLongClick! $position", Toast.LENGTH_SHORT).show()
                        Log.e("DoneList", "onLongClick $position")
                        Log.e("SameList", Global.sameRecyclerView.toString())
                        Log.e("SamePosition", Global.samePosition.toString())
                    }

                    override fun onDoubleTap(view: View?, position: Int) {


                        // Write your code here
                        Log.e("DoneList", "onDoubleTap $position")
                    }
                })
        )


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

