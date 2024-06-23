package com.example.githubuserrev1.kumpulan_viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.kumpulan_data.kebutuhan_api.ApiConfig
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.user_model.ResponUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Mendeklarasikan kelas HomeViewModel yang mewarisi dari ViewModel
class HomeViewModel : ViewModel() {

    // Mendeklarasikan MutableLiveData untuk menyimpan daftar pengguna
    private val _listUser = MutableLiveData<List<ListUser>>()
    // Mendeklarasikan LiveData untuk expose daftar pengguna ke observer
    val listUser: LiveData<List<ListUser>> = _listUser

    // Mendeklarasikan MutableLiveData untuk menyimpan status loading
    private val _isLoading = MutableLiveData<Boolean>()
    // Mendeklarasikan LiveData untuk expose status loading ke observer
    val isLoading: LiveData<Boolean> = _isLoading

    // Fungsi untuk mencari pengguna berdasarkan query
    fun findUser(query: String) {
        // Mengatur nilai isLoading menjadi true
        _isLoading.value = true
        // Mendapatkan instance ApiService dan memanggil fungsi mendapatkanUsersDariSearch
        val client = ApiConfig.mendapatkanAPIService().mendapatkanUsersDariSearch(query)

        // Melakukan enqueue pada client untuk menjalankan permintaan secara asynchronous
        client.enqueue(object : Callback<ResponUser> {
            // Fungsi yang dipanggil saat respons berhasil diterima
            override fun onResponse(
                call: Call<ResponUser>,
                response: Response<ResponUser>
            ) {
                // Mengatur nilai isLoading menjadi false
                _isLoading.value = false
                // Jika respons sukses, mengatur nilai listUser dengan data dari respons
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.users
                } else {
                    // Jika respons gagal, mencetak pesan kesalahan ke log
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            // Fungsi yang dipanggil saat permintaan gagal
            override fun onFailure(call: Call<ResponUser>, t: Throwable) {
                // Mengatur nilai isLoading menjadi false
                _isLoading.value = false
                // Mencetak pesan kesalahan ke log
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // Companion object untuk mendefinisikan konstanta
    companion object {
        private const val TAG = "HomeViewModel"
    }
}