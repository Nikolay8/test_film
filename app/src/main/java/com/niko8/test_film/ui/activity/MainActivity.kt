package com.niko8.test_film.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niko8.test_film.R
import com.niko8.test_film.data.PageResponse
import com.niko8.test_film.network.ApiClient
import com.niko8.test_film.network.OnResultListener
import com.niko8.test_film.ui.adapter.FilmItemAdapter
import com.niko8.test_film.ui.fragments.DetailFragment

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var searchView: SearchView? = null
    private var recyclerView: RecyclerView? = null
    private var filmList: MutableList<PageResponse.FilmModel> = mutableListOf()
    private var searchFilmList: MutableList<PageResponse.FilmModel> = mutableListOf()
    private var searchRequest: String? = null
    private var filmAdapter: FilmItemAdapter? = null
    private var searchFilmAdapter: FilmItemAdapter? = null
    private var currentPage = 1
    private var searchPage = 1
    private val apiClient = ApiClient()

    private lateinit var vm: MainViewModel

    companion object {
        const val ERROR_TAG = "ERROR"
        const val DETAIL_FRAGMENT_TAG = "detailFragmentTag"
        const val MAX_PAGES_LIMIT = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AAA", "Activity Created")
        vm = ViewModelProvider(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

        initViews()
        getPage(currentPage)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideSearchView(false)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView?.layoutManager = layoutManager

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchRequest = p0.toString()
                if (searchRequest.isNullOrEmpty()) {
                    clearSearchData()
                    getPage(currentPage)
                } else {
                    searchByString(searchRequest)
                }
                return true
            }

        })
    }

    private fun getPage(page: Int) {
        apiClient.getDataPage(page, object : OnResultListener {
            override fun onSuccess(pageResponse: PageResponse) {
                filmList.addAll(pageResponse.results)
                updatedAdapter(false, filmList)
            }

            override fun onError(message: String?) {
                Log.e(ERROR_TAG, message.toString())
                showErrorToast()
            }
        })
    }

    private fun searchByString(searchRequest: String?) {
        if (!searchRequest.isNullOrEmpty()) {
            apiClient.searchFilm(searchRequest, searchPage, false, object : OnResultListener {
                override fun onSuccess(pageResponse: PageResponse) {
                    if (searchPage == 1) {
                        searchFilmList.clear()
                    }
                    searchFilmList.addAll(pageResponse.results)
                    updatedAdapter(true, searchFilmList)
                }

                override fun onError(message: String?) {
                    Log.e(ERROR_TAG, message.toString())
                    showErrorToast()
                }

            })
        }
    }

    private fun updatedAdapter(
        isSearchFlow: Boolean,
        filmList: MutableList<PageResponse.FilmModel>
    ) {
        if (isSearchFlow) {
            if (searchFilmAdapter != null) {
                searchFilmAdapter!!.notifyDataSetChanged()
            } else {
                searchFilmAdapter = FilmItemAdapter(
                    this,
                    searchFilmList,
                    object : FilmItemAdapter.NeedNewPage {
                        override fun nextPageRandom() {
                            if (searchPage + 1 <= MAX_PAGES_LIMIT) {
                                searchPage += 1
                                searchByString(searchRequest)
                            }
                        }

                        override fun nextPageSearch() {

                        }

                    }, object : FilmItemAdapter.OnImageClickCallback {
                        override fun onFilmClick(selectedFilm: PageResponse.FilmModel) {
                            addDetailFragment(selectedFilm)
                        }

                    })
                recyclerView?.adapter = searchFilmAdapter
            }
        } else {
            if (filmAdapter != null) {
                filmAdapter!!.notifyDataSetChanged()
            } else {
                filmAdapter = FilmItemAdapter(
                    this,
                    filmList,
                    object : FilmItemAdapter.NeedNewPage {
                        override fun nextPageRandom() {
                            if (currentPage + 1 <= MAX_PAGES_LIMIT) {
                                currentPage += 1
                                getPage(currentPage)
                            }
                        }

                        override fun nextPageSearch() {

                        }

                    }, object : FilmItemAdapter.OnImageClickCallback {
                        override fun onFilmClick(selectedFilm: PageResponse.FilmModel) {
                            addDetailFragment(selectedFilm)
                        }
                    })
                recyclerView?.adapter = filmAdapter
            }
        }
    }

    private fun addDetailFragment(filmModel: PageResponse.FilmModel) {
        val fragment = DetailFragment.newInstance(filmModel)
        hideSearchView(true)
        try {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(
                    R.id.fragment_container,
                    fragment,
                    DETAIL_FRAGMENT_TAG
                )
                .addToBackStack(DETAIL_FRAGMENT_TAG)
                .commit()
        } catch (illExc: IllegalArgumentException) {
            Log.e(ERROR_TAG, illExc.toString())
        } catch (nullExc: NullPointerException) {
            Log.e(ERROR_TAG, nullExc.toString())
        }
    }

    private fun hideSearchView(isHide: Boolean) {
        if (isHide) {
            searchView?.visibility = View.GONE
            toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
            toolbar?.setNavigationOnClickListener {
                onBackPressed()
                toolbar?.navigationIcon = null
                searchView?.visibility = View.VISIBLE
            }
        } else {
            toolbar?.navigationIcon = null
            searchView?.visibility = View.VISIBLE
        }
    }

    private fun clearSearchData() {
        searchFilmList.clear()
        searchPage = 1
        filmAdapter = null
        searchFilmAdapter = null
    }

    private fun showErrorToast() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }
}