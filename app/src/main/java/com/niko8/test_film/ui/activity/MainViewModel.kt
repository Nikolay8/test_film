package com.niko8.test_film.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    init {
        Log.d("AAA","VM Created")
    }

    override fun onCleared() {
        Log.d("AAA","VM Cleared")
        super.onCleared()
    }

}