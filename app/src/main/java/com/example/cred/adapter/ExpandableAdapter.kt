package com.example.cred.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.cred.R
import com.example.cred.expandable.ExpandableLayout
import kotlinx.android.synthetic.main.expanded_item.view.*


class ExpandableAdapter(
    private val recyclerView: RecyclerView,
    private val items: List<String>,
    private val onItemClick: (String, Int) -> Unit
) :
    RecyclerView.Adapter<ExpandableAdapter.ExpandableViewHolder>() {

    companion object {
        private const val UNSELECTED = -1
        private var selectedItem: Int = UNSELECTED
    }

    inner class ExpandableViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

        fun bind(value: String, position: Int) {

            val pos = adapterPosition
            val isSelected = pos == selectedItem
            item.textViewTitle.isSelected = isSelected
            item.expandableLayout.setExpanded(isSelected,false)
            bindExpandableView(value, item, position)
        }

        private fun bindExpandableView(value: String, item: View, position: Int) {

            item.textViewTitle.text = value
            item.textViewItem.text = value

            item.expandableLayout.apply {
                setInterpolator(OvershootInterpolator())
                setOnExpansionUpdateListener(object : ExpandableLayout.OnExpansionUpdateListener {
                    override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
                        if (state == ExpandableLayout.State.EXPANDING) {
                           onItemClick(value,adapterPosition)
                        }
                    }
                })
            }
            item.textViewTitle.setOnClickListener {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(selectedItem)
                if (viewHolder != null) {
                    item.textViewTitle.isSelected = false
                    item.expandableLayout.collapse()
                }
                val pos = adapterPosition
                if (pos == selectedItem) {
                    selectedItem = UNSELECTED
                } else {
                    item.textViewTitle.isSelected = true
                    item.expandableLayout.expand()
                    selectedItem = pos
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.expanded_item, parent, false)
        return ExpandableViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}