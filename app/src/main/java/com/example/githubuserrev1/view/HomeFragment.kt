package com.example.githubuserrev1.view

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
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.utils.Helper
import com.example.githubuserrev1.utils.PengaturanPreferences
import com.example.githubuserrev1.view.adapter.ListUserAdapter
import com.example.githubuserrev1.viewModel.HomeViewModel
import com.example.githubuserrev1.viewModel.PengaturanViewModel
import com.example.githubuserrev1.viewModel.PengaturanViewModelFactory
import java.util.ArrayList
import kotlin.system.exitProcess

class HomeFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: FragmentHomeBinding
    private lateinit var settingViewModel : PengaturanViewModel
    private val homeViewModel by viewModels<HomeViewModel>()
    private val helper = Helper()
    private var listUser = ArrayList<ListUser>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "GitHubUser"

        setViewModel()
        darkModeCheck()

        context?.let { searchUsername() }
        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser -> setUser(listUser) }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.isLoading(
                it,
                binding.progressBarHome
            )
        }

        val binding = binding.recyclerViewHome
        binding.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opsi_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.favoriteMenu ->{
                NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_homeFragment_to_favoriteFragment)
                return true
            }
            R.id.setting -> {
                NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_homeFragment_to_settingFragment)
                true
            }
            R.id.exit -> {
                activity?.finish()
                exitProcess(0)
            }
            else -> true
        }
    }

    private fun searchUsername() {
        val searchView = binding.searchViewHome
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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

    private fun setUser(users: List<ListUser>) {

        val listUser = ArrayList<ListUser>()

        for (user in users) {
            listUser.clear()
            listUser.addAll(users)
        }

        val adapter = ListUserAdapter(listUser)
        binding.recyclerViewHome.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUser) {
                showUser(data)
            }
        })
    }

    private fun showUser(data: ListUser) {
        val moveDataUser = Bundle()
        moveDataUser.putParcelable(EXTRA_USER, data)
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_homeFragment_to_userDetailFragment, moveDataUser)
    }

    private fun setViewModel(){
        val pref = PengaturanPreferences.mendapatkanInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, PengaturanViewModelFactory(pref))[PengaturanViewModel::class.java]
    }

    private fun darkModeCheck(){
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkTheme ->
            if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}


