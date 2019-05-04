package com.booleansystems.codechallenge.ui.home.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.booleansystems.codechallenge.R
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.booleansystems.codechallenge.ui.home.view.adapter.SearchGalleryResultAdapter
import com.booleansystems.codechallenge.ui.home.viewmodel.SearchGalleryViewModel
import com.booleansystems.codechallenge.utils.DebounceSearchViewObservale
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity(), Observer<MutableList<GalleryImage>> {


    var mDisposableSearch: Disposable? = null

    var mViewModel: SearchGalleryViewModel? = null

    var mAdapter: SearchGalleryResultAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initListManager()
    }

    fun initViewModel() {
        mViewModel = getViewModel()
        mViewModel!!.mSearchResultList.observe(this, this)
    }

    fun initListManager() {
        mAdapter = SearchGalleryResultAdapter(this, emptyList<GalleryImage>().toMutableList())
        rvSearchResults.layoutManager = GridLayoutManager(this, 2);
        rvSearchResults.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val menuItemSearch = menu!!.findItem(R.id.action_search)
        val searchView = menuItemSearch.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        initalizeSearchDebounce(searchView)
        return true
    }

    fun initalizeSearchDebounce(searchView: SearchView) {
        mDisposableSearch = Observable.create(DebounceSearchViewObservale(searchView))
            .map { text -> text.toLowerCase() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                mViewModel!!.startSearchGallery(true, text)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposableSearch!!.dispose()
    }

    override fun onChanged(t: MutableList<GalleryImage>?) {
        mAdapter!!.notifyDataSetChanged(mViewModel!!.mRestartSearch.value!!, t!!)
    }
}
