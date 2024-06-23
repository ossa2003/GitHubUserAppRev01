package com.example.githubuserrev1.kumpulan_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.databinding.ItemListUserBinding
import com.example.githubuserrev1.user_model.ListUser

// Mendeklarasikan kelas ListUserAdapter yang mewarisi dari RecyclerView.Adapter
// Kelas ini menerima daftar ArrayList ListUser dan menggunakan ViewHolder bernama ListViewHolder
class ListUserAdapter(private val users: ArrayList<ListUser>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    // Deklarasi variabel untuk menyimpan callback item click
    private lateinit var onItemClickCallback: OnItemClickCallback

    // Fungsi untuk membuat ViewHolder dan meng-inflate layout item list user
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    // Fungsi untuk mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]

        // Menggunakan Glide untuk memuat gambar avatar user ke dalam ImageView
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_block)
            .into(holder.binding.imgItemPhoto)

        // Mengatur teks username pada TextView
        holder.binding.tvItemUsername.text = user.username

        // Mengatur callback ketika item diklik
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    // Fungsi untuk mengembalikan jumlah item dalam adapter
    override fun getItemCount(): Int = users.size

    // Kelas ViewHolder untuk item list user
    class ListViewHolder(var binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root)

    // Fungsi untuk mengatur callback item click
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Interface untuk mendefinisikan callback item click
    interface OnItemClickCallback {
        fun onItemClicked(data: ListUser)
    }
}