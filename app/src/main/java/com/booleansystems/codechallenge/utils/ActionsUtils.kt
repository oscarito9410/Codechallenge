package com.booleansystems.codechallenge.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.booleansystems.codechallenge.R


/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
class ActionsUtils {
    companion object {

        fun shareLinkAction(context: Context, url: String){
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, url)
            context.startActivity(Intent.createChooser(i, context.getString(R.string.text_share_with)))
        }

        fun openBrowersAction(context: Context, url: String) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }
    }
}