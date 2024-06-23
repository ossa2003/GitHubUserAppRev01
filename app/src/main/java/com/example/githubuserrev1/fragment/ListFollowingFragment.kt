package com.example.githubuserrev1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.databinding.FragmentListFollowingBinding
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.kumpulan_utils.Helper
import com.example.githubuserrev1.kumpulan_adapter.ListFollowingAdapter
import com.example.githubuserrev1.kumpulan_viewModel.ListFollowingViewModel

// Mendeklarasikan kelas ListFollowingFragment yang mewarisi dari Fragment
class ListFollowingFragment : Fragment() {

    // Deklarasi variabel untuk binding dengan FragmentListFollowingBinding
    private lateinit var binding: FragmentListFollowingBinding
    // Mendapatkan instance ListFollowingViewModel menggunakan delegasi viewModels
    private val listfollowingViewModel by viewModels<ListFollowingViewModel>()
    // Membuat instance Helper
    private val helper = Helper()

    // Fungsi untuk meng-inflate layout fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout untuk fragment ini
        binding = FragmentListFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi untuk mengatur view setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengamati perubahan status loading di ViewModel
        listfollowingViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.sedangLoading(it, binding.progressBarFollowing)
        }

        // Mengamati perubahan data listUser di ViewModel
        listfollowingViewModel.listUser.observe(viewLifecycleOwner) { followingList ->
            setRecyclerData(followingList)
        }

        // Mendapatkan daftar following berdasarkan username yang diterima sebagai argumen
        listfollowingViewModel.mendapatkanFollowing(
            arguments?.getString(DetilUserFragment.EXTRA_USERNAME).toString()
        )
    }

    // Fungsi untuk mengatur data pengguna yang diikuti dalam recycler view
    private fun setRecyclerData(list: List<ListUser>) {
        with(binding) {
            val followingList = ArrayList<ListUser>()
            // Membersihkan dan menambahkan semua data ke dalam followingList
            for (user in list) {
                followingList.clear()
                followingList.addAll(list)
            }

            // Mengatur layout manager untuk recycler view
            rvFollowing.layoutManager = LinearLayoutManager(context)
            // Mengatur adapter untuk recycler view
            val followingAdapter = ListFollowingAdapter(followingList)
            rvFollowing.adapter = followingAdapter
        }
    }
}