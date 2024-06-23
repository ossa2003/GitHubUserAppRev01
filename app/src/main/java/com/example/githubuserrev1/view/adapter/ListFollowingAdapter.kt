package com.example.githubuserrev1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.databinding.ItemListFollowingBinding
import com.example.githubuserrev1.model.ListUser

class ListFollowingAdapter(private val listUserFollowing: List<ListUser>) :
    RecyclerView.Adapter<ListFollowingAdapter.FollowingViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding =
            ItemListFollowingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = listUserFollowing[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_block)
            .into(holder.binding.userFollow)
        holder.binding.tvFollowUsername.text = user.username
    }

    override fun getItemCount() = listUserFollowing.size

    class FollowingViewHolder(var binding: ItemListFollowingBinding) :
        RecyclerView.ViewHolder(binding.root)
}