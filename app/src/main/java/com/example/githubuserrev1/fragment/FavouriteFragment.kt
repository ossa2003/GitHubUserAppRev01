package com.example.githubuserrev1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.R
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite
import com.example.githubuserrev1.databinding.FragmentListFavouriteBinding
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.kumpulan_adapter.FavouriteAdapter
import com.example.githubuserrev1.kumpulan_viewModel.FavouriteViewModel
import com.example.githubuserrev1.kumpulan_viewModel.ViewModelFactory
import java.util.ArrayList

// Mendeklarasikan kelas FavouriteFragment yang mewarisi dari Fragment
class FavouriteFragment : Fragment() {

    // Deklarasi variabel untuk binding dengan FragmentListFavouriteBinding
    private var _binding: FragmentListFavouriteBinding? = null
    // Deklarasi variabel untuk mendapatkan binding yang tidak null
    private val binding get() = _binding

    // Deklarasi variabel untuk ViewModel
    private lateinit var favoriteViewModel: FavouriteViewModel

    // Fungsi untuk meng-inflate layout fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        _binding = FragmentListFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // Fungsi untuk mengatur view setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Mengatur judul ActionBar menjadi "Favorite"
        (activity as AppCompatActivity).supportActionBar?.title = "Favorite"

        // Mendapatkan instance ViewModel
        favoriteViewModel = obtainViewModel(this)
        // Mengamati perubahan data pada ViewModel
        favoriteViewModel.cariSemua().observe(viewLifecycleOwner) { favoriteList ->
            // Memanggil fungsi untuk mengatur data RecyclerView
            setRecyclerData(favoriteList)
        }
    }

    // Fungsi untuk mengatur data pada RecyclerView
    private fun setRecyclerData(list: List<Favourite>) {
        // Membuat daftar favoriteList
        val favoriteList = ArrayList<Favourite>()

        // Mengisi daftar favoriteList dengan data dari parameter list
        for (user in list) {
            favoriteList.clear()
            favoriteList.addAll(list)
        }

        // Mengatur layout manager dan adapter untuk RecyclerView
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(context)
        val favoriteAdapter = FavouriteAdapter(favoriteList)
        binding?.rvFavorite?.adapter = favoriteAdapter

        // Mengatur callback untuk item klik pada adapter
        favoriteAdapter.setOnItemClickCallback(object : FavouriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListUser) {
                // Memanggil fungsi untuk menampilkan data user
                showUser(data)
            }
        })
    }

    // Fungsi untuk menampilkan detail user
    private fun showUser(data: ListUser) {
        // Membuat bundle untuk mengirim data user
        val moveDataUser = Bundle()
        moveDataUser.putParcelable(HomeFragment.EXTRA_USER, data)
        // Menavigasi ke userDetailFragment dengan data yang dikirim
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_favoriteFragment_to_userDetailFragment, moveDataUser)
    }

    // Fungsi untuk mendapatkan instance ViewModel
    private fun obtainViewModel(activity: Fragment): FavouriteViewModel {
        val factory = ViewModelFactory.mendapatkanInstance(activity.requireActivity().application)
        return ViewModelProvider(activity, factory)[FavouriteViewModel::class.java]
    }

    // Companion object untuk mendefinisikan konstanta
    companion object {
        const val EXTRA_USER = "extra_user"
    }
}
