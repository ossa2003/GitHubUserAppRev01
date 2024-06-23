package com.example.githubuserrev1.kumpulan_viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.kumpulan_data.FavouriteRepository
import com.example.githubuserrev1.kumpulan_data.kebutuhan_api.ApiConfig
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite
import com.example.githubuserrev1.user_model.ListUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Mendeklarasikan kelas DetilUserViewModel yang mewarisi dari ViewModel
class DetilUserViewModel(application: Application) : ViewModel() {

    // Deklarasi variabel MutableLiveData untuk menyimpan data user
    private val _user = MutableLiveData<ListUser>()
    // Deklarasi variabel LiveData untuk expose data user ke observer
    val user: LiveData<ListUser> = _user

    // Deklarasi variabel MutableLiveData untuk menyimpan status loading
    private val _isLoading = MutableLiveData<Boolean>()
    // Deklarasi variabel LiveData untuk expose status loading ke observer
    val isLoading: LiveData<Boolean> = _isLoading

    // Deklarasi instance FavouriteRepository untuk mengakses data favorit
    private val favouriteRepository: FavouriteRepository = FavouriteRepository(application)

    // Fungsi untuk mendapatkan detail user berdasarkan username
    fun mendapatDetailUser(username: String) {
        // Mengatur nilai isLoading menjadi true untuk menampilkan loading indicator
        _isLoading.value = true
        // Mendapatkan instance APIService dan memanggil fungsi mendapatkanDetailUser
        val client = ApiConfig.mendapatkanAPIService().mendapatkanDetailUser(username)

        // Melakukan enque pada panggilan API
        client.enqueue(object : Callback<ListUser> {
            // Fungsi yang dipanggil ketika respons berhasil diterima
            override fun onResponse(
                call: Call<ListUser>,
                response: Response<ListUser>
            ) {
                // Mengatur nilai isLoading menjadi false untuk menyembunyikan loading indicator
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        // Mengatur nilai _user dengan data dari respons
                        _user.value = response.body()
                    }
                } else {
                    // Menampilkan log pesan kesalahan jika respons tidak berhasil
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            // Fungsi yang dipanggil ketika terjadi kesalahan saat memanggil API
            override fun onFailure(call: Call<ListUser>, t: Throwable) {
                // Mengatur nilai isLoading menjadi false untuk menyembunyikan loading indicator
                _isLoading.value = false
                // Menampilkan log pesan kesalahan jika terjadi kegagalan
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // Fungsi untuk menambahkan data favorite ke dalam database
    fun buat(favorite: Favourite) {
        return favouriteRepository.buat(favorite)
    }

    // Fungsi untuk menghapus data favorite dari database berdasarkan id
    fun hapus(id: Long?) {
        return favouriteRepository.hapus(id)
    }

    // Fungsi untuk mencari data favorite berdasarkan username
    fun cariByID(username: String?) : LiveData<List<Favourite>>  {
        return favouriteRepository.cariByID(username)
    }

    // Companion object untuk mendefinisikan konstanta
    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}