package com.example.githubuserrev1.view

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
import com.example.githubuserrev1.data.local.entity.Favourite
import com.example.githubuserrev1.databinding.FragmentDetilUserBinding
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.utils.Helper
import com.example.githubuserrev1.view.adapter.SectionPagerAdapter
import com.example.githubuserrev1.viewModel.DetilUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetilUserFragment : Fragment() {

    private var _binding: FragmentDetilUserBinding? = null
    private val binding get() = _binding

    private lateinit var username: String
    private lateinit var detailUserViewModel: DetilUserViewModel

    private val helper = Helper()
    private var buttonState: Boolean = false
    private var favorite: Favourite? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetilUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mengaturUsername()
        setViewPager()
        detailUserViewModel = obtainViewModel(this)
        detailUserViewModel.mendapatDetailUser(username)
        detailUserViewModel.cariByID(username).observe(viewLifecycleOwner) { favoriteData ->
            buttonState = favoriteData.isNotEmpty()
            if (buttonState) {
                binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active)
            } else {
                binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_border)
            }
        }
        detailUserViewModel.user.observe(viewLifecycleOwner) { user ->
            mengaturUser(user)
            binding?.buttonFavorite?.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active)

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
                    detailUserViewModel.buat(favorite as Favourite)
                    Toast.makeText(
                        context,
                        "User Added to Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    buttonState = false
                    binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_border)
                    detailUserViewModel.hapus(user.id)
                    Toast.makeText(
                        context,
                        "User Deleted From Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            binding?.progressBarGroup?.let { it1 ->
                helper.isLoading(
                    it,
                    it1
                )
            }
        }
        binding?.buttonFavorite?.setOnClickListener {
            binding?.buttonFavorite?.setImageResource(R.drawable.ic_favorite_active)
        }

    }

    private fun mengaturUsername() {
        val user = arguments?.getParcelable<ListUser>(HomeFragment.EXTRA_USER) as ListUser
        this.username = user.username.toString()
    }

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

    private fun obtainViewModel(activity: Fragment): DetilUserViewModel {
        val factory = com.example.githubuserrev1.viewModel.ViewModelFactory.mendapatkanInstance(activity.requireActivity().application)
        return ViewModelProvider(activity, factory)[DetilUserViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}