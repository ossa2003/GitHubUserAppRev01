package com.example.githubuserrev1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserrev1.R
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite
import com.example.githubuserrev1.databinding.FragmentDetilUserBinding
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.kumpulan_utils.Helper
import com.example.githubuserrev1.kumpulan_adapter.SectionPagerAdapter
import com.example.githubuserrev1.kumpulan_viewModel.DetilUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// Mendeklarasikan kelas DetilUserFragment yang mewarisi dari Fragment
class DetilUserFragment : Fragment() {

    // Deklarasi variabel untuk binding dengan FragmentDetilUserBinding
    private var _binding: FragmentDetilUserBinding? = null
    // Deklarasi variabel untuk mendapatkan binding yang tidak null
    private val binding get() = _binding

    // Deklarasi variabel untuk menyimpan username
    private lateinit var username: String
    // Deklarasi variabel untuk ViewModel
    private lateinit var detailUserViewModel: DetilUserViewModel

    // Membuat instance Helper
    private val helper = Helper()
    // Deklarasi variabel untuk menyimpan status button
    private var buttonState: Boolean = false
    // Deklarasi variabel untuk menyimpan data Favorite
    private var favorite: Favourite? = null

    // Fungsi untuk meng-inflate layout fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        _binding = FragmentDetilUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // Fungsi untuk mengatur view setelah fragment di-create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // Mengaktifkan opsi menu di fragment
        mengaturUsername() // Memanggil fungsi untuk mengatur username
        setViewPager() // Memanggil fungsi untuk mengatur ViewPager
        detailUserViewModel = obtainViewModel(this) // Mendapatkan instance ViewModel
        detailUserViewModel.mendapatDetailUser(username) // Mendapatkan detail user dari ViewModel
        detailUserViewModel.cariByID(username).observe(viewLifecycleOwner) { favoriteData ->
            buttonState = favoriteData.isNotEmpty() // Mengatur status button berdasarkan data favorite
            if (buttonState) {
                binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active) // Mengatur icon button
            } else {
                binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_border) // Mengatur icon button
            }
        }
        detailUserViewModel.user.observe(viewLifecycleOwner) { user ->
            mengaturUser(user) // Memanggil fungsi untuk mengatur data user di view
            binding?.buttonFavorite?.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active) // Mengubah icon button ke state active

                    // Membuat instance Favourite dan menyimpan data user ke dalamnya
                    favorite = Favourite(
                        user.username,
                        user.name,
                        user.avatarUrl,
                        user.followers,
                        user.following,
                        user.followersUrl,
                        user.followingUrl,
                        user.repository,
                        user.location,
                        user.company,
                        user.id
                    )
                    detailUserViewModel.buat(favorite as Favourite) // Menyimpan data favorite ke database
                    Toast.makeText(
                        context,
                        "User ditambahkan ke Favourite",
                        Toast.LENGTH_SHORT
                    ).show() // Menampilkan toast
                } else {
                    buttonState = false
                    binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_border) // Mengubah icon button ke state non-active
                    detailUserViewModel.hapus(user.id) // Menghapus data favorite dari database
                    Toast.makeText(
                        context,
                        "User dihapus dari Favourite",
                        Toast.LENGTH_SHORT
                    ).show() // Menampilkan toast
                }
            }
        }
        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            binding?.progressBarGroup?.let { it1 ->
                helper.sedangLoading(
                    it,
                    it1
                )
            }
        }
        binding?.buttonFavorite?.setOnClickListener {
            binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active)
        }

    }

    // Fungsi untuk mengatur username dari arguments yang diterima
    private fun mengaturUsername() {
        val user = arguments?.getParcelable<ListUser>(HomeFragment.EXTRA_USER) as ListUser
        this.username = user.username.toString()
    }

    // Fungsi untuk mengatur data user di view
    private fun mengaturUser(user: ListUser) {
        binding?.userName?.text = StringBuilder("@").append(user.username)
        binding?.name?.text = user.name
        binding?.company?.text = user.company ?: "-----"
        binding?.location?.text = user.location ?: "-----"
        binding?.repository?.text = StringBuilder(user.repository.toString()).append(" Repository")
        binding?.followers?.text = StringBuilder(user.followers.toString()).append(" Followers")
        binding?.following?.text = StringBuilder(user.following.toString()).append(" Following")
        binding?.imageProfile?.let {
            Glide.with(this.requireContext())
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_block)
                .into(it)
        }
    }

    // Fungsi untuk mengatur ViewPager dan TabLayout
    private fun setViewPager() {
        val viewPager: ViewPager2? = view?.findViewById(R.id.view_pager)
        val tabs: TabLayout? = view?.findViewById(R.id.tabs)

        val username = Bundle()
        username.putString(EXTRA_USERNAME, this.username)

        val sectionsPagerAdapter = SectionPagerAdapter(childFragmentManager, lifecycle, username)

        viewPager?.adapter = sectionsPagerAdapter
        if (viewPager != null && tabs != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    // Fungsi untuk mendapatkan instance ViewModel
    private fun obtainViewModel(activity: Fragment): DetilUserViewModel {
        val factory = com.example.githubuserrev1.kumpulan_viewModel.ViewModelFactory.mendapatkanInstance(activity.requireActivity().application)
        return ViewModelProvider(activity, factory)[DetilUserViewModel::class.java]
    }

    // Fungsi yang dipanggil saat fragment dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Menghapus binding untuk mencegah memory leak
    }

    // Companion object untuk mendefinisikan konstanta
    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
