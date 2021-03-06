package com.hmei.kanban

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DragListener internal constructor(private val listener: CustomListener) :
    View.OnDragListener {
    private var isDropped = false
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val frameLayoutItem = R.id.frame_layout_item
                val emptyTextView1 = R.id.empty_list_text_view_1
                val emptyTextView2 = R.id.empty_list_text_view_2
                val emptyTextView3 = R.id.empty_list_text_view_3
                val recyclerView1 = R.id.recycler_view_todo
                val recyclerView2 = R.id.recycler_view_in_progress
                val recyclerView3 = R.id.recycler_view_done
                when (viewId) {
                    frameLayoutItem, emptyTextView1, emptyTextView2, emptyTextView3, recyclerView1, recyclerView2, recyclerView3 -> {
                        val target: RecyclerView
                        when (viewId) {
                            emptyTextView1, recyclerView1 -> target =
                                v.rootView.findViewById<View>(recyclerView1) as RecyclerView
                            emptyTextView2, recyclerView2 -> target =
                                v.rootView.findViewById<View>(recyclerView2) as RecyclerView
                            emptyTextView3, recyclerView3 -> target =
                                v.rootView.findViewById<View>(recyclerView3) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }

                        }
                        if (viewSource != null) {
                            val source = viewSource.parent as RecyclerView
                            val adapterSource = source.adapter as CustomAdapter?
                            val positionSource = viewSource.tag as Int
                            val list: String? = adapterSource?.getList()?.get(positionSource)
                            val listSource = adapterSource?.getList()?.apply {
                                removeAt(positionSource)
                            }
                            listSource?.let { adapterSource.updateList(it) }
                            adapterSource?.notifyDataSetChanged()
                            val adapterTarget = target.adapter as CustomAdapter?
                            val customListTarget = adapterTarget?.getList()
                            if (positionTarget >= 0) {
                                list?.let { customListTarget?.add(positionTarget, it) }
                            } else {
                                list?.let { customListTarget?.add(it) }
                            }
                            customListTarget?.let { adapterTarget.updateList(it) }
                            adapterTarget?.notifyDataSetChanged()
                            Global.sameRecyclerView = target.id == source.id
                            Global.samePosition=positionTarget==positionSource
                            var category: String
                            val allEntries = ArrayList<ListItemEntity>()
                            val todoEntries = ArrayList<ListItemEntity>()
                            val inProgressEntries = ArrayList<ListItemEntity>()
                            val doneEntries = ArrayList<ListItemEntity>()
                            when (target.id) {
                                recyclerView1 -> {
                                    customListTarget?.forEach {
                                        todoEntries.add(ListItemEntity(it, "todo"))
                                    }
                                    Global.todoList=todoEntries
                                }
                                recyclerView2 -> {
                                    customListTarget?.forEach {
                                        inProgressEntries.add(ListItemEntity(it, "in_progress"))
                                    }
                                    Global.inProgressList=inProgressEntries
                                }
                                recyclerView3 -> {
                                    customListTarget?.forEach {
                                        doneEntries.add(ListItemEntity(it, "done"))
                                    }
                                    Global.doneList=doneEntries
                                }
                            }
                            when (source.id) {
                                recyclerView1 -> {
                                    listSource?.forEach {
                                        todoEntries.add(ListItemEntity(it, "todo"))
                                    }
                                    Global.todoList=todoEntries
                                }
                                recyclerView2 -> {
                                    listSource?.forEach {
                                        inProgressEntries.add(ListItemEntity(it, "in_progress"))
                                    }
                                    Global.inProgressList=inProgressEntries
                                }
                                recyclerView3 -> {
                                    listSource?.forEach {
                                        doneEntries.add(ListItemEntity(it, "done"))
                                    }
                                    Global.doneList=doneEntries
                                }
                            }
                            Global.fullList.clear()
                            Global.fullList= (Global.todoList+Global.inProgressList+Global.doneList) as ArrayList<ListItemEntity>
                            Log.e("Full", Global.fullList.toString())

                            if (source.id == recyclerView3 && adapterSource?.itemCount ?: 0 < 1) {
                                listener.setEmptyList(View.VISIBLE, recyclerView3, emptyTextView3)
                            }
                            if (viewId == emptyTextView3) {
                                listener.setEmptyList(View.GONE, recyclerView3, emptyTextView3)
                            }
                            if (source.id == recyclerView2 && adapterSource?.itemCount ?: 0 < 1) {
                                listener.setEmptyList(View.VISIBLE, recyclerView2, emptyTextView2)
                            }
                            if (viewId == emptyTextView2) {
                                listener.setEmptyList(View.GONE, recyclerView2, emptyTextView2)
                            }
                            if (source.id == recyclerView1 && adapterSource?.itemCount ?: 0 < 1) {
                                listener.setEmptyList(View.VISIBLE, recyclerView1, emptyTextView1)
                            }
                            if (viewId == emptyTextView1) {
                                listener.setEmptyList(View.GONE, recyclerView1, emptyTextView1)
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}