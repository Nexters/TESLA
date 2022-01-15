package com.ozcoin.cookiepang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}