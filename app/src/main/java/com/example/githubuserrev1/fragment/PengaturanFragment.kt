package com.example.githubuserrev1.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.ViewModelProvider
import com.example.githubuserrev1.databinding.FragmentPengaturanBinding
import com.example.githubuserrev1.kumpulan_utils.PengaturanPreferences
import com.example.githubuserrev1.kumpulan_viewModel.PengaturanViewModel
import com.example.githubuserrev1.kumpulan_viewModel.PengaturanViewModelFactory

class PengaturanFragment : Fragment() {

    // Membuat instance DataStore untuk menyimpan preferensi pengguna
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // Binding untuk mengakses elemen tampilan pada fragment_pengaturan.xml
    private var _binding: FragmentPengaturanBinding? = null
    private val binding get() = _binding

    // Meng-inflate layout untuk fragment ini
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengaturanBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // Mengatur tampilan setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Setting "

        // Mendapatkan instance dari PengaturanPreferences
        val pref = PengaturanPreferences.mendapatkanInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(this, PengaturanViewModelFactory(pref))[PengaturanViewModel::class.java]

        // Mengamati perubahan pada pengaturan tema
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.switchTheme?.isChecked = false
            }
        }

        // Mengatur switch untuk mengubah pengaturan tema
        binding?.switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}
