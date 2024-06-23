package com.example.githubuserrev1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuserrev1.databinding.ActivityMainBinding

// Mendeklarasikan kelas MainActivity yang mewarisi dari AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Mendeklarasikan variabel binding untuk mengakses view dari ActivityMainBinding
    private lateinit var binding: ActivityMainBinding

    // Menimpa (override) fungsi onCreate untuk mengatur aktivitas saat dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Meng-inflate layout ActivityMainBinding dan mengatur variabel binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Mengatur konten view dari aktivitas dengan root view dari binding
        setContentView(binding.root)
    }
}