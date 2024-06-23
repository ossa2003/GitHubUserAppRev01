package com.example.githubuserrev1.kumpulan_adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserrev1.fragment.ListFollowersFragment
import com.example.githubuserrev1.fragment.ListFollowingFragment

// Mendeklarasikan kelas SectionPagerAdapter yang mewarisi dari FragmentStateAdapter
// Kelas ini menerima FragmentManager, Lifecycle, dan Bundle username sebagai parameter
class SectionPagerAdapter(
    activity: FragmentManager, lifecycle: Lifecycle,
    private val username: Bundle
) : FragmentStateAdapter(activity, lifecycle) {

    // Fungsi untuk membuat fragment berdasarkan posisi
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        // Menggunakan when statement untuk menentukan fragment yang akan dibuat berdasarkan posisi
        when (position) {
            0 -> {
                fragment = ListFollowersFragment() // Membuat instance ListFollowersFragment untuk posisi 0
            }
            1 -> {
                fragment = ListFollowingFragment() // Membuat instance ListFollowingFragment untuk posisi 1
            }
        }
        // Menetapkan arguments ke fragment menggunakan username
        fragment?.arguments = username
        return fragment as Fragment // Mengembalikan fragment yang telah dibuat
    }

    // Fungsi untuk mengembalikan jumlah item (fragment) dalam adapter
    override fun getItemCount(): Int = 2
}