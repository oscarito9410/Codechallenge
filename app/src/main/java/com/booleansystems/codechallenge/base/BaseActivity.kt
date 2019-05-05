package com.booleansystems.codechallenge.base

import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
abstract class BaseActivity : AppCompatActivity() {

    var mSnackbar: Snackbar? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }

        }
        return true
    }

    fun showSnackBar(view: View, @StringRes message: Int) {
        if (mSnackbar == null) {
            mSnackbar = Snackbar.make(
                view, message,
                Snackbar.LENGTH_LONG
            )
        }
        if (!mSnackbar!!.isShownOrQueued)
            mSnackbar!!.show()
    }


}