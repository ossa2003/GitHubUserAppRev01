package com.example.githubuserrev1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserrev1.R
import com.example.githubuserrev1.data.local.entity.Favourite
import com.example.githubuserrev1.databinding.FragmentListFavouriteBinding
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.view.adapter.FavouriteAdapter
import com.example.githubuserrev1.viewModel.FavouriteViewModel
import com.example.githubuserrev1.viewModel.ViewModelFactory
import java.util.ArrayList

class FavouriteFragment : Fragment() {

    private var _binding: FragmentListFavouriteBinding? = null
    private val binding get() = _binding

    private lateinit var favoriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Favorite"

        favoriteViewModel = obtainViewModel(this)
        favoriteViewModel.cariSemua().observe(viewLifecycleOwner) { favoriteList ->
            setRecyclerData(favoriteList)
        }
    }

    private fun setRecyclerData(list: List<Favourite>) {

        val favoriteList = ArrayList<Favourite>()

        for (user in list) {
            favoriteList.clear()
            favoriteList.addAll(list)
        }

        binding?.rvFavorite?.layoutManager = LinearLayoutManager(context)
        val favoriteAdapter = FavouriteAdapter(favoriteList)
        binding?.rvFavorite?.adapter = favoriteAdapter

        favoriteAdapter.setOnItemClickCallback(object : FavouriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListUser) {
                showUser(data)
            }
        })
    }


    private fun showUser(data: ListUser) {
        val moveDataUser = Bundle()
        moveDataUser.putParcelable(HomeFragment.EXTRA_USER, data)
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_favoriteFragment_to_userDetailFragment, moveDataUser)
    }

    private fun obtainViewModel(activity: Fragment): FavouriteViewModel {
        val factory = ViewModelFactory.mendapatkanInstance(activity.requireActivity().application)
        return ViewModelProvider(activity, factory)[FavouriteViewModel::class.java]
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}