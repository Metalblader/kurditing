package com.example.kurditing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kurditing.utils.Preferences
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        tv_judul.text = intent.getStringExtra("judul")
        tv_owner.text = intent.getStringExtra("owner")
        tv_harga.text = intent.getStringExtra("harga")

        iv_back.setOnClickListener(){
            finish()
        }
    }
}