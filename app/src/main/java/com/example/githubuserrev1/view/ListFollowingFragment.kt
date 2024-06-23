package com.example.githubuserrev1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.databinding.FragmentListFollowingBinding
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.utils.Helper
import com.example.githubuserrev1.view.adapter.ListFollowingAdapter
import com.example.githubuserrev1.viewModel.ListFollowingViewModel

class ListFollowingFragment : Fragment() {

    private lateinit var binding: FragmentListFollowingBinding
    private val listfollowingViewModel by viewModels<ListFollowingViewModel>()
    private val helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listfollowingViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.isLoading(it, binding.progressBarFollowing)
        }
        listfollowingViewModel.listUser.observe(viewLifecycleOwner) { followingList ->
            setRecyclerData(followingList)
        }
        listfollowingViewModel.mendapatkanFollowing(
            arguments?.getString(DetilUserFragment.EXTRA_USERNAME).toString()
        )
    }

    private fun setRecyclerData(list: List<ListUser>) {

        with(binding) {
            val followingList = ArrayList<ListUser>()
            for (user in list) {
                followingList.clear()
                followingList.addAll(list)
            }

            rvFollowing.layoutManager = LinearLayoutManager(context)
            val followingAdapter = ListFollowingAdapter(followingList)
            rvFollowing.adapter = followingAdapter
        }
    }
}