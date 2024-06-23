package com.example.githubuserrev1.kumpulan_data

// Mendeklarasikan kelas sealed yang bernama Hasil dengan tipe covariant out R
sealed class Hasil<out R> private constructor() {

    // Mendefinisikan data class Success dengan properti data
    data class Success<out T>(val data: T) : Hasil<T>()
    // Kelas ini digunakan untuk mewakili hasil yang sukses, menyimpan data dari tipe generik T.

    // Mendefinisikan data class Error dengan properti error
    data class Error(val error: String) : Hasil<Nothing>()
    // Kelas ini digunakan untuk mewakili hasil yang gagal, menyimpan pesan kesalahan sebagai String.

    // Mendefinisikan objek singleton Loading
    object Loading : Hasil<Nothing>()
    // Objek ini digunakan untuk mewakili status loading (sedang memuat), tanpa data tambahan.
}