package com.booleansystems.codechallenge.base

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.annotation.NonNull
import com.booleansystems.codechallenge.R

class BaseLoaderDialog(@NonNull context: Context) : Dialog(context, R.style.CustomDialogTranslucent) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loader_layout)
        setCancelable(false)
    }


}
