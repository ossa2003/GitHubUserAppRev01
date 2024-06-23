package com.example.githubuserrev1.kumpulan_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.databinding.ItemListFollowingBinding
import com.example.githubuserrev1.user_model.ListUser

// Mendeklarasikan kelas ListFollowingAdapter yang mewarisi dari RecyclerView.Adapter
// Kelas ini menerima daftar ListUser dan menggunakan ViewHolder bernama FollowingViewHolder
class ListFollowingAdapter(private val listUserFollowing: List<ListUser>) :
    RecyclerView.Adapter<ListFollowingAdapter.FollowingViewHolder>() {

    // Fungsi untuk membuat ViewHolder dan meng-inflate layout item list following
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding =
            ItemListFollowingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FollowingViewHolder(binding)
    }

    // Fungsi untuk mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = listUserFollowing[position]

        // Menggunakan Glide untuk memuat gambar avatar user ke dalam ImageView
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_block)
            .into(holder.binding.userFollow)
        // Mengatur teks username pada TextView
        holder.binding.tvFollowUsername.text = user.username
    }

    // Fungsi untuk mengembalikan jumlah item dalam adapter
    override fun getItemCount() = listUserFollowing.size

    // Kelas ViewHolder untuk item list following
    class FollowingViewHolder(var binding: ItemListFollowingBinding) :
        RecyclerView.ViewHolder(binding.root)
}