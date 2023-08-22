package com.example.xlmp13.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xlmp13.database.Item
import com.example.xlmp13.databinding.ItemListItemBinding
import com.example.xlmp13.generated.callback.OnClickListener

class ItemListListAdapter(
    private val onItemClicked: (Item) -> Unit
): ListAdapter<Item, ItemListListAdapter.ItemListViewHolder>(
    DiffCallBack
) {

    inner class ItemListViewHolder(
        private var binding: ItemListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.name==newItem.name
                    && oldItem.price==newItem.price
                    && oldItem.quantity==newItem.quantity
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemListListAdapter.ItemListViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
        return holder.bind(item)
    }
}