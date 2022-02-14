package com.ozcoin.cookiepang.extensions

import androidx.appcompat.app.AppCompatActivity
import com.ozcoin.cookiepang.domain.dialog.DialogContents
import com.ozcoin.cookiepang.ui.dialog.TwoBtnDialog

fun AppCompatActivity.showTwoBtnDialog(dialogContents: DialogContents, callback: (Boolean) -> Unit) {
    TwoBtnDialog(dialogContents, callback).show(supportFragmentManager, "")
}
