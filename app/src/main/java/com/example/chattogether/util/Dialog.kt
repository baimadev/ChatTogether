package com.example.chattogether.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.icu.text.CaseMap

interface DialogButtonClickListener {
    fun onClickButton(dialog: DialogInterface)
}

object Dialog {
    fun createDialog(
        context: Context,
        title: String? = null,
        message: String? = null,
        positiveText: String? = null,
        positiveClickListener: DialogButtonClickListener? = null,
        negativeText: String? = null,
        negativeClickListener: DialogButtonClickListener? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
        title?.let { builder.setTitle(it) }
        message?.let { builder.setMessage(it) }
        positiveText?.let {
            builder.setPositiveButton(positiveText) { dialogInterface, i ->
                positiveClickListener?.onClickButton(dialogInterface)
            }
        }
        negativeText?.let {
            builder.setNegativeButton(negativeText) { dialogInterface, i ->
                negativeClickListener?.onClickButton(dialogInterface)
            }
        }
        return builder.create()
    }


}


