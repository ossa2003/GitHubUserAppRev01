package com.example.githubuserrev1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.databinding.ItemListFollowingBinding
import com.example.githubuserrev1.model.ListUser

class ListFollowersAdapter(private val listUserFollowers: List<ListUser>) :
    RecyclerView.Adapter<ListFollowersAdapter.FollowersViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding =
            ItemListFollowingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val user = listUserFollowers[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_block)
            .into(holder.binding.userFollow)
        holder.binding.tvFollowUsername.text = user.username
    }

    override fun getItemCount() = listUserFollowers.size

    class FollowersViewHolder(var binding: ItemListFollowingBinding) :
        RecyclerView.ViewHolder(binding.root)
}