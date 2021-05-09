package com.hmei.kanban

import android.content.ClipData
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife


internal class ListAdapter(var list: List<String>, private val listener: Listener?) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(), OnTouchListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.list_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.text!!.text = list[position]
        holder.frameLayout!!.tag = position
        holder.frameLayout!!.setOnTouchListener(this)
        holder.frameLayout!!.setOnDragListener(listener?.let { DragListener(it) })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v.startDrag(data, shadowBuilder, v, 0)
                }
                return true
            }
        }
        return false
    }

    fun updateList(list: List<String>) {
        this.list = list
    }

    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(listener)
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!")
            null
        }

    internal inner class ListViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        @BindView(R.id.text)
        var text: TextView? = null

        @BindView(R.id.frame_layout_item)
        var frameLayout: FrameLayout? = null

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }
}
