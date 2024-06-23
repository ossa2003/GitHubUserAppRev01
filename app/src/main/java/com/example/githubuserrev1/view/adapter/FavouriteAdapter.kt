package com.example.githubuserrev1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.data.local.entity.Favourite
import com.example.githubuserrev1.databinding.ItemListUserBinding
import com.example.githubuserrev1.model.ListUser

class FavouriteAdapter(private val listFavorites: List<Favourite>) :
    RecyclerView.Adapter<FavouriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = listFavorites[position]
        val user = ListUser(
            favorite.username,
            favorite.id,
            favorite.name,
            favorite.followers,
            favorite.following,
            favorite.followersUrl,
            favorite.followingUrl,
            favorite.repository,
            favorite.location,
            favorite.company,
            favorite.avatarUrl
        )

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(favorite.avatarUrl)
                .placeholder(R.drawable.ic_block)
                .into(imgItemPhoto)

            tvItemUsername.text = favorite.username
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
        }
    }

    override fun getItemCount() = listFavorites.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteViewHolder(var binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ListUser)
    }
}