package com.hmei.kanban

import com.hmei.kanban.R
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class DragListener internal constructor(private val listener: Listener) :
    View.OnDragListener {
    private var isDropped = false
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource: View = event.localState as View
                val viewId: Int = v.getId()
                val flItem: Int = R.id.frame_layout_item
                val tvEmptyListTop: Int = R.id.tvEmptyListTop
                val tvEmptyListBottom: Int = R.id.tvEmptyListBottom
                val rvTop: Int = R.id.rvTop
                val rvBottom: Int = R.id.rvBottom
                when (viewId) {
                    flItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                        val target: RecyclerView
                        when (viewId) {
                            tvEmptyListTop, rvTop -> target = v.getRootView().findViewById(rvTop)
                            tvEmptyListBottom, rvBottom -> target =
                                v.getRootView().findViewById(rvBottom)
                            else -> {
                                target = v.getParent()
                                positionTarget = v.getTag()
                            }
                        }
                        if (viewSource != null) {
                            val source = viewSource.getParent() as RecyclerView
                            val adapterSource = source.adapter as ListAdapter?
                            val positionSource = viewSource.getTag() as Int
                            val sourceId = source.id
                            val list = adapterSource!!.list[positionSource]
                            val listSource = adapterSource.list
                            listSource.removeAt(positionSource)
                            adapterSource.updateList(listSource)
                            adapterSource.notifyDataSetChanged()
                            val adapterTarget = target.adapter as ListAdapter?
                            val customListTarget = adapterTarget!!.list
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list)
                            } else {
                                customListTarget.add(list)
                            }
                            adapterTarget.updateList(customListTarget)
                            adapterTarget.notifyDataSetChanged()
                            if (sourceId == rvBottom && adapterSource.itemCount < 1) {
                                listener.setEmptyListBottom(true)
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false)
                            }
                            if (sourceId == rvTop && adapterSource.itemCount < 1) {
                                listener.setEmptyListTop(true)
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false)
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).setVisibility(View.VISIBLE)
        }
        return true
    }
}