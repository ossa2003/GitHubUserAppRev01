package com.example.githubuserrev1.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserrev1.view.ListFollowersFragment
import com.example.githubuserrev1.view.ListFollowingFragment

class SectionPagerAdapter(
    activity: FragmentManager, lifecycle: Lifecycle,
    private val username: Bundle
) : FragmentStateAdapter(activity, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ListFollowersFragment()
            }
            1 -> {
                fragment = ListFollowingFragment()
            }
        }
        fragment?.arguments = username
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}