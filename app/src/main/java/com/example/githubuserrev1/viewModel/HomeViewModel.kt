package com.example.githubuserrev1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.data.api.ApiConfig
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.model.ResponUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ListUser>>()
    val listUser: LiveData<List<ListUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUser(query : String) {
        _isLoading.value = true
        val client = ApiConfig.mendapatkanAPIService().mendapatkanUsersDariSearch(query)

        client.enqueue(object : Callback<ResponUser> {
            override fun onResponse(
                call: Call<ResponUser>,
                response: Response<ResponUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.users
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }
}