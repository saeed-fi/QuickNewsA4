package com.example.quicknews.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quicknews.R
import com.example.quicknews.data.model.Post
import com.example.quicknews.databinding.ItemPostBinding

class PostAdapter(
    private val onItemClick: (Post) -> Unit,
    private val onEdit: (Post) -> Unit,
    private val onDelete: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.apply {
                tvTitle.text = post.title
                tvBody.text = post.body

                // Item click
                root.setOnClickListener { onItemClick(post) }
                tvMore.setOnClickListener { onItemClick(post) }

                // Popup menu
                btnMenu.setOnClickListener {
                    showPopupMenu(post)
                }

                // Context menu
                root.setOnCreateContextMenuListener { menu, _, _ ->
                    menu.add("Edit").setOnMenuItemClickListener {
                        onEdit(post)
                        true
                    }
                    menu.add("Delete").setOnMenuItemClickListener {
                        onDelete(post)
                        true
                    }
                }
            }
        }

        private fun showPopupMenu(post: Post) {
            val popup = PopupMenu(binding.root.context, binding.btnMenu)
            popup.inflate(R.menu.menu_item_popup)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        onEdit(post)
                        true
                    }
                    R.id.action_delete -> {
                        onDelete(post)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}