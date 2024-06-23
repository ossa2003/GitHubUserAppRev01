//package com.example.githubuserrev1.kumpulan_utils
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.fragment.Window
//import com.example.githubuserrev1.MainActivity
//import com.example.githubuserrev1.R
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//class SplashScreenActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.activity_splash_screen)
//        supportActionBar?.hide()
//
//        GlobalScope.launch(Dispatchers.Default) {
//            delay(1500L)
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//            finish()
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        }
//    }
//}