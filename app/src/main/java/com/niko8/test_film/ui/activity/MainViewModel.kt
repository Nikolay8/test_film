package com.niko8.test_film.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niko8.test_film.data.PageResponse
import com.niko8.test_film.network.ApiClient
import com.niko8.test_film.network.OnResultListener

class MainViewModel : ViewModel() {

    private val apiClient = ApiClient()
    private var filmList: MutableLiveData<List<PageResponse.FilmModel>> = MutableLiveData()
    private var searchFilmList: MutableLiveData<List<PageResponse.FilmModel>> = MutableLiveData()
    private var errorData: MutableLiveData<String> = MutableLiveData()

    init {
        Log.d("AAA", "VM Created")
    }

    override fun onCleared() {
        Log.d("AAA", "VM Cleared")
        super.onCleared()
    }

    fun getFilmList(): LiveData<List<PageResponse.FilmModel>> {
        return filmList
    }

    fun getSearchFilmList(): LiveData<List<PageResponse.FilmModel>> {
        return searchFilmList
    }

    fun getErrorData(): MutableLiveData<String> {
        return errorData
    }

    fun getPage(page: Int) {
        apiClient.getDataPage(page, object : OnResultListener {
            override fun onSuccess(pageResponse: PageResponse) {
                filmList.value = pageResponse.results
            }

            override fun onError(message: String?) {
                Log.e(MainActivity.ERROR_TAG, message.toString())
                if (message != null) {
                    errorData.postValue(message!!)
                }
            }
        })
    }

    fun searchByString(searchRequest: String?, searchPage: Int) {
        if (!searchRequest.isNullOrEmpty()) {
            apiClient.searchFilm(searchRequest, searchPage, false, object : OnResultListener {
                override fun onSuccess(pageResponse: PageResponse) {
                    if (searchPage == 1) {
                        searchFilmList.value = emptyList()
                    }
                    searchFilmList.value = pageResponse.results
                }

                override fun onError(message: String?) {
                    Log.e(MainActivity.ERROR_TAG, message.toString())
                    errorData.postValue(message!!)
                }
            })
        }
    }
}