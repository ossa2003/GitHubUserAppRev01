package com.example.githubuserrev1.kumpulan_viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.kumpulan_data.kebutuhan_api.ApiConfig
import com.example.githubuserrev1.user_model.ListUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Mendeklarasikan kelas ListFollowersViewModel yang mewarisi dari ViewModel
class ListFollowersViewModel : ViewModel() {

    // Mendeklarasikan objek companion untuk menyimpan konstanta
    companion object {
        private const val TAG = "FollowersModel" // Konstanta TAG untuk logging
    }

    // Mendeklarasikan MutableLiveData untuk menyimpan daftar pengguna
    private val _listUser = MutableLiveData<List<ListUser>>()
    // Mendeklarasikan LiveData untuk expose daftar pengguna ke observer
    val listUser: LiveData<List<ListUser>> = _listUser

    // Mendeklarasikan MutableLiveData untuk menyimpan status loading
    private val _isLoading = MutableLiveData<Boolean>()
    // Mendeklarasikan LiveData untuk expose status loading ke observer
    val isLoading: LiveData<Boolean> = _isLoading

    // Fungsi internal untuk mendapatkan daftar pengikut (followers) berdasarkan query
    internal fun mendapatkanFollowers(query: String) {
        // Mengatur nilai isLoading menjadi true
        _isLoading.value = true
        // Mendapatkan instance ApiService dan memanggil fungsi mendapatkanListFollowers
        val client = ApiConfig.mendapatkanAPIService().mendapatkanListFollowers(query)
        // Melakukan enqueue pada client untuk menjalankan permintaan secara asynchronous
        client.enqueue(object : Callback<List<ListUser>> {
            // Fungsi yang dipanggil ketika permintaan API berhasil
            override fun onResponse(
                call: Call<List<ListUser>>,
                response: Response<List<ListUser>>
            ) {
                // Mengatur nilai isLoading menjadi false
                _isLoading.value = false
                // Memeriksa apakah response berhasil
                if (response.isSuccessful) {
                    // Mengatur nilai _listUser dengan data dari response body
                    _listUser.value = response.body()
                } else {
                    // Logging pesan error jika response gagal
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            // Fungsi yang dipanggil ketika permintaan API gagal
            override fun onFailure(call: Call<List<ListUser>>, t: Throwable) {
                // Mengatur nilai isLoading menjadi false
                _isLoading.value = false
                // Logging pesan error jika permintaan API gagal
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}