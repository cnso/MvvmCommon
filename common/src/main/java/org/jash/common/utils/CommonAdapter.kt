package org.jash.common.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CommonAdapter<D>(val layout:(D) -> Pair<Int, Int>, val data:MutableList<D> = mutableListOf()): Adapter<CommonViewHolder>() {
    override fun getItemViewType(position: Int): Int = layout(data[position]).first

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder =
        CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val d = data[position]
        holder.binding.setVariable(layout(d).second, d)
    }

    operator fun plusAssign(list: List<D>) {
        val size = data.size
        data += list
        notifyItemRangeInserted(size, list.size)
    }

    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }
    fun removeIf(filter:(D) -> Boolean):Int {
        val position = data.indexOfFirst(filter)
        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
        return position
    }
}
class CommonViewHolder(val binding:ViewDataBinding):ViewHolder(binding.root)