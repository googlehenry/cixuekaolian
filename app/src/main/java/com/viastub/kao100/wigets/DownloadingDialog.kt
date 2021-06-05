package com.viastub.kao100.wigets

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StyleRes
import com.viastub.kao100.R
import kotlinx.android.synthetic.main.dialog_downloading_layout.*

class DownloadingDialog : Dialog {
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(
        context,
        cancelable,
        cancelListener
    )

    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)

    constructor(context: Context) : super(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_downloading_layout)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog_btn_comfirm.setOnClickListener { this.dismiss() }
    }

}