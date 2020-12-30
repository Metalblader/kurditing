package com.example.kurditing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_terms.*
import kotlinx.android.synthetic.main.activity_terms.iv_back

class ReferalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referal)

        iv_back.setOnClickListener(){
            finish();
        }

    }

}