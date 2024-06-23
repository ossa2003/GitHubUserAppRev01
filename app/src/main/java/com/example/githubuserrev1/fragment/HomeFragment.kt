package com.example.githubuserrev1.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.R
import com.example.githubuserrev1.databinding.FragmentHomeBinding
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.kumpulan_utils.Helper
import com.example.githubuserrev1.kumpulan_utils.PengaturanPreferences
import com.example.githubuserrev1.kumpulan_adapter.ListUserAdapter
import com.example.githubuserrev1.kumpulan_viewModel.HomeViewModel
import com.example.githubuserrev1.kumpulan_viewModel.PengaturanViewModel
import com.example.githubuserrev1.kumpulan_viewModel.PengaturanViewModelFactory
import java.util.ArrayList
import kotlin.system.exitProcess

// Mendeklarasikan kelas HomeFragment yang mewarisi dari Fragment
class HomeFragment : Fragment() {

    // Mendefinisikan DataStore Preferences dengan ekstensi pada Context
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // Deklarasi variabel binding untuk mengakses view
    private lateinit var binding: FragmentHomeBinding

    // Deklarasi variabel untuk ViewModel pengaturan
    private lateinit var settingViewModel: PengaturanViewModel

    // Mendapatkan instance HomeViewModel menggunakan delegasi viewModels
    private val homeViewModel by viewModels<HomeViewModel>()

    // Membuat instance Helper
    private val helper = Helper()

    // Deklarasi variabel untuk menyimpan daftar user
    private var listUser = ArrayList<ListUser>()

    // Fungsi untuk meng-inflate layout fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout untuk fragment ini
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi untuk mengatur view setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // Mengaktifkan opsi menu di fragment
        (activity as AppCompatActivity).supportActionBar?.title = "GitHub Search User" // Mengatur judul ActionBar

        setViewModel() // Memanggil fungsi untuk mengatur ViewModel
        darkModeCheck() // Memanggil fungsi untuk memeriksa mode gelap

        context?.let { searchUsername() } // Memanggil fungsi untuk mengatur pencarian username
        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser -> setUser(listUser) } // Mengamati perubahan data listUser
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.sedangLoading(it, binding.progressBarHome) // Menampilkan atau menyembunyikan progress bar berdasarkan isLoading
        }

        val binding = binding.recyclerViewHome
        binding.layoutManager = LinearLayoutManager(activity) // Mengatur layout manager untuk recycler view
    }

    // Fungsi untuk meng-inflate menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opsi_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    // Fungsi untuk menangani item menu yang dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favoriteMenu -> {
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_homeFragment_to_favoriteFragment) // Navigasi ke fragment favorite
                true
            }
            R.id.setting -> {
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_homeFragment_to_settingFragment) // Navigasi ke fragment setting
                true
            }
            R.id.exit -> {
                activity?.finish() // Mengakhiri aktivitas
                exitProcess(0) // Keluar dari aplikasi
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Fungsi untuk mengatur pencarian username
    private fun searchUsername() {
        val searchView = binding.searchViewHome
        searchView.queryHint = resources.getString(R.string.search_hint) // Mengatur hint pada search view
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.findUser(query)
                query.let {
                    binding.recyclerViewHome.visibility = View.VISIBLE
                    homeViewModel.findUser(it)
                    setUser(listUser)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    // Fungsi untuk mengatur data user dalam recycler view
    private fun setUser(users: List<ListUser>) {
        val listUser = ArrayList<ListUser>()
        listUser.clear()
        listUser.addAll(users)

        val adapter = ListUserAdapter(listUser)
        binding.recyclerViewHome.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUser) {
                showUser(data)
            }
        })
    }

    // Fungsi untuk menampilkan detail user saat item diklik
    private fun showUser(data: ListUser) {
        val moveDataUser = Bundle()
        moveDataUser.putParcelable(EXTRA_USER, data)
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_homeFragment_to_userDetailFragment, moveDataUser)
    }

    // Fungsi untuk mengatur ViewModel pengaturan
    private fun setViewModel() {
        val pref = PengaturanPreferences.mendapatkanInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, PengaturanViewModelFactory(pref))[PengaturanViewModel::class.java]
    }

    // Fungsi untuk memeriksa mode gelap
    private fun darkModeCheck() {
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkTheme ->
            if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // Companion object untuk mendefinisikan konstanta
    companion object {
        const val EXTRA_USER = "extra_user"
    }
}