package com.booleansystems.codechallenge.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Created by oscar on 04/05/19
 * operez@na-at.com.mx
 */
class DebounceSearchViewObservale(private val mSearchview: SearchView) : ObservableOnSubscribe<String> {
    @Throws(Exception::class)
    override fun subscribe(emitter: ObservableEmitter<String>) {
        mSearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                emitter.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                emitter.onNext(newText)
                return false
            }
        })
    }
}
