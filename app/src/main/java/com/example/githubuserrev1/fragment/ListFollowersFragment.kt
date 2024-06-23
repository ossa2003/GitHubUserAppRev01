package com.example.githubuserrev1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.databinding.FragmentListFollowersBinding
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.kumpulan_utils.Helper
import com.example.githubuserrev1.kumpulan_adapter.ListFollowersAdapter
import com.example.githubuserrev1.kumpulan_viewModel.ListFollowersViewModel

// Mendeklarasikan kelas ListFollowersFragment yang mewarisi dari Fragment
class ListFollowersFragment : Fragment() {

    // Deklarasi variabel untuk binding dengan FragmentListFollowersBinding
    private var _binding: FragmentListFollowersBinding? = null
    // Deklarasi variabel untuk mendapatkan binding yang tidak null
    private val binding get() = _binding
    // Mendapatkan instance ListFollowersViewModel menggunakan delegasi viewModels
    private val followersViewModel by viewModels<ListFollowersViewModel>()
    // Membuat instance Helper
    private val helper = Helper()

    // Fungsi untuk meng-inflate layout fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        _binding = FragmentListFollowersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    // Fungsi untuk mengatur view setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengamati perubahan isLoading pada followersViewModel dan mengatur progress bar
        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            binding?.progressBarFollowers?.let { it1 -> helper.sedangLoading(it, it1) }
        }

        // Mengamati perubahan listUser pada followersViewModel dan mengatur data di recycler view
        followersViewModel.listUser.observe(viewLifecycleOwner) { followerList ->
            setRecyclerData(followerList)
        }

        // Mendapatkan daftar followers berdasarkan username yang diterima dari arguments
        followersViewModel.mendapatkanFollowers(
            arguments?.getString(DetilUserFragment.EXTRA_USERNAME).toString()
        )
    }

    // Fungsi untuk mengatur data di recycler view
    private fun setRecyclerData(list: List<ListUser>) {

        val followersList = ArrayList<ListUser>()
        // Menghapus dan menambahkan semua item dari list ke followersList
        for (user in list) {
            followersList.clear()
            followersList.addAll(list)
        }

        // Mengatur layout manager untuk recycler view
        binding?.rvFollowers?.layoutManager = LinearLayoutManager(context)
        // Membuat instance ListFollowersAdapter dan mengatur adapter untuk recycler view
        val followersAdapter = ListFollowersAdapter(followersList)
        binding?.rvFollowers?.adapter = followersAdapter
    }

    // Fungsi yang dipanggil saat fragment dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        // Menghapus binding untuk mencegah memory leak
        _binding = null
    }
}