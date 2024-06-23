package com.example.githubuserrev1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.databinding.FragmentListFollowersBinding
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.utils.Helper
import com.example.githubuserrev1.view.adapter.ListFollowersAdapter
import com.example.githubuserrev1.viewModel.ListFollowersViewModel

class ListFollowersFragment : Fragment() {

    private var _binding: FragmentListFollowersBinding? = null
    private val binding get() = _binding
    private val followersViewModel by viewModels<ListFollowersViewModel>()
    private val helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListFollowersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            binding?.progressBarFollowers?.let { it1 -> helper.isLoading(it, it1) }
        }

        followersViewModel.listUser.observe(viewLifecycleOwner) { followerList ->
            setRecyclerData(followerList)
        }

        followersViewModel.mendapatkanFollowers(
            arguments?.getString(DetilUserFragment.EXTRA_USERNAME).toString()
        )
    }

    private fun setRecyclerData(list: List<ListUser>) {

        val followersList = ArrayList<ListUser>()
        for (user in list) {
            followersList.clear()
            followersList.addAll(list)
        }

        binding?.rvFollowers?.layoutManager = LinearLayoutManager(context)
        val followersAdapter = ListFollowersAdapter(followersList)
        binding?.rvFollowers?.adapter = followersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}