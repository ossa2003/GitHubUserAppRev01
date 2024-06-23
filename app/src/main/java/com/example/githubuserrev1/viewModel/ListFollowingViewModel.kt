package com.example.githubuserrev1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.data.api.ApiConfig
import com.example.githubuserrev1.model.ListUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowingViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowersModel"
    }

    private val _listUser = MutableLiveData<List<ListUser>>()
    val listUser: LiveData<List<ListUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun mendapatkanFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.mendapatkanAPIService().mendapatkanListFollowing(query)
        client.enqueue(object : Callback<List<ListUser>> {
            override fun onResponse(
                call: Call<List<ListUser>>,
                response: Response<List<ListUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}