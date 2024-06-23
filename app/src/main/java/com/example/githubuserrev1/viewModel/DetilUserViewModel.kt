package com.example.githubuserrev1.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.data.FavouriteRepository
import com.example.githubuserrev1.data.api.ApiConfig
import com.example.githubuserrev1.data.local.entity.Favourite
import com.example.githubuserrev1.model.ListUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetilUserViewModel(application: Application) : ViewModel() {

    private val _user = MutableLiveData<ListUser>()
    val user: LiveData<ListUser> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favouriteRepository: FavouriteRepository = FavouriteRepository(application)

    fun mendapatDetailUser(username: String) {

        _isLoading.value = true
        val client = ApiConfig.mendapatkanAPIService().mendapatkanDetailUser(username)

        client.enqueue(object : Callback<ListUser> {
            override fun onResponse(
                call: Call<ListUser>,
                response: Response<ListUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _user.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun buat(favorite: Favourite) {
        return favouriteRepository.buat(favorite)
    }

    fun hapus(id: Long?) {
        return favouriteRepository.hapus(id)
    }

    fun cariByID(username: String?) : LiveData<List<Favourite>>  {
        return favouriteRepository.cariByID(username)
    }


    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}