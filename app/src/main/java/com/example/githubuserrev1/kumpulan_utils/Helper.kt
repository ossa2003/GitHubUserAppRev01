package com.example.githubuserrev1.kumpulan_utils

import android.view.View

// Mendeklarasikan kelas Helper
class Helper {

    // Mendefinisikan fungsi isLoading dengan dua parameter: status bertipe Boolean dan fragment bertipe View
    fun sedangLoading(status: Boolean, view: View) {
        // Jika status bernilai true, maka setel visibilitas fragment menjadi VISIBLE
        if (status) view.visibility = View.VISIBLE
        // Jika status bernilai false, maka setel visibilitas fragment menjadi INVISIBLE
        else view.visibility = View.INVISIBLE
    }
}
