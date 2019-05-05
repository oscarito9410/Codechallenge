package com.booleansystems.codechallenge.ui.home.view

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.booleansystems.codechallenge.R
import com.booleansystems.codechallenge.base.BaseActivity
import com.booleansystems.codechallenge.ui.detail.DetailFullActivity
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.booleansystems.codechallenge.ui.home.view.adapter.ItemClickListener
import com.booleansystems.codechallenge.ui.home.view.adapter.SearchGalleryResultAdapter
import com.booleansystems.codechallenge.ui.home.viewmodel.SearchGalleryViewModel
import com.booleansystems.codechallenge.utils.extentions.isConnected
import com.booleansystems.codechallenge.utils.widgets.recyclerview.PaginationScrollListener
import com.booleansystems.codechallenge.utils.widgets.recyclerview.SpacesItemDecoration
import com.booleansystems.codechallenge.utils.widgets.searchview.DebounceSearchViewObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit


class HomeActivity : BaseActivity(), Observer<MutableList<GalleryImage>>, ItemClickListener {

    var mDisposableSearch: Disposable? = null

    var mViewModel: SearchGalleryViewModel? = null

    var mAdapter: SearchGalleryResultAdapter? = null

    var mSearchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initListManager()
    }

    fun initViewModel() {
        mViewModel = getViewModel()
        mViewModel!!.mSearchResultList.observe(this, this)
        mViewModel!!.mErrorEvent.observe(this, Observer { t -> showSnackBar(lyRootHome, t!!) })
        mViewModel!!.mNotItemsFoundNewSearch.observe(this, Observer { toggleEmptyAnimation(!it) })
        mViewModel!!.mIsLoading.observe(this, Observer
        {
            if (it!!) lottieAnimationSearchHome.visibility = VISIBLE else lottieAnimationSearchHome.visibility = GONE
        })

    }

    fun initListManager() {
        mAdapter = SearchGalleryResultAdapter(this, emptyList<GalleryImage>().toMutableList(), this)
        val decoration = SpacesItemDecoration(16)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearchResultsHome.layoutManager = manager
        rvSearchResultsHome.addItemDecoration(decoration)
        addScrollListener(manager)
        rvSearchResultsHome.adapter = mAdapter
    }

    fun addScrollListener(manager: StaggeredGridLayoutManager) {
        rvSearchResultsHome.addOnScrollListener(object : PaginationScrollListener(manager) {
            override fun isLastPage(): Boolean {
                return false
            }

            override fun isLoading(): Boolean {
                return mViewModel!!.mIsLoading.value!!
            }

            override fun loadMoreItems() {
                if (mSearchView != null)
                    mViewModel!!.startSearchGallery(isConnected, false, mSearchView!!.query.toString())
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE
        val columns = if (isLandscape) 3 else 2
        val manager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
        rvSearchResultsHome.layoutManager = manager
        addScrollListener(manager)
        mAdapter!!.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val menuItemSearch = menu!!.findItem(R.id.action_search)
        mSearchView = menuItemSearch.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        initalizeSearchDebounce(mSearchView!!)
        return true
    }

    fun initalizeSearchDebounce(searchView: SearchView) {
        mDisposableSearch = Observable.create(
            DebounceSearchViewObservable(
                searchView
            )
        )
            .map { text -> text.toLowerCase() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .filter { text -> text.isNotBlank() && text.length >= 3 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                mViewModel!!.startSearchGallery(isConnected, true, text)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposableSearch != null)
            mDisposableSearch!!.dispose()
    }

    override fun onChanged(t: MutableList<GalleryImage>?) {
        toggleEmptyAnimation(t!!.isNotEmpty())
        mAdapter!!.notifyDataSetChanged(mViewModel!!.mRestartSearch.value!!, t)
    }

    fun toggleEmptyAnimation(hasItems: Boolean) {
        if (!hasItems) {
            lottieAnimationSearchEmpty.playAnimation()
            lottieAnimationSearchEmpty.visibility = VISIBLE
            rvSearchResultsHome.visibility = GONE
        } else {
            rvSearchResultsHome.visibility = VISIBLE
            lottieAnimationSearchEmpty.cancelAnimation()
            lottieAnimationSearchEmpty.visibility = GONE
        }
    }

    override fun onItemClicked(item: Any, view: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, view,
            ViewCompat.getTransitionName(view)!!
        )
        startActivity(
            DetailFullActivity.getStartIntent(this, item as GalleryImage),
            options.toBundle()
        );
    }
}
