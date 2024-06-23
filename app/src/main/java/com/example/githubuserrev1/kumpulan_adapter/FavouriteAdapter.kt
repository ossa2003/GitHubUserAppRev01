package com.example.githubuserrev1.kumpulan_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite
import com.example.githubuserrev1.databinding.ItemListUserBinding
import com.example.githubuserrev1.user_model.ListUser

// Mendeklarasikan kelas FavouriteAdapter yang mewarisi dari RecyclerView.Adapter
// Kelas ini menerima daftar Favorite dan menggunakan ViewHolder bernama FavoriteViewHolder
class FavouriteAdapter(private val listFavorites: List<Favourite>) :
    RecyclerView.Adapter<FavouriteAdapter.FavoriteViewHolder>() {

    // Deklarasi variabel untuk menyimpan callback item click
    private lateinit var onItemClickCallback: OnItemClickCallback

    // Fungsi untuk membuat ViewHolder dan meng-inflate layout item list user
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteViewHolder(binding)
    }

    // Fungsi untuk mengikat data ke ViewHolder
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

        // Menggunakan holder.binding untuk mengatur tampilan item
        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(favorite.avatarUrl)
                .placeholder(R.drawable.ic_block)
                .into(imgItemPhoto)

            tvItemUsername.text = favorite.username
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
        }
    }

    // Fungsi untuk mengembalikan jumlah item dalam adapter
    override fun getItemCount() = listFavorites.size

    // Fungsi untuk mengatur callback item click
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Kelas ViewHolder untuk item list user
    class FavoriteViewHolder(var binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Interface untuk mendefinisikan callback item click
    interface OnItemClickCallback {
        fun onItemClicked(data: ListUser)
    }
}