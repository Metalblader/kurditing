package com.example.kurditing

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_referal.*
import kotlinx.android.synthetic.main.activity_terms.iv_back


class ReferalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referal)

        iv_back.setOnClickListener(){
            finish();
        }
        btn_ajak_teman.setOnClickListener(){
            ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Share with....")
                .setText("https://kurditing.com/?ref=wilson")
                .startChooser()
        }

        btn_salin_link.setOnClickListener(){
            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label.toString(),"https://kurditing.com/?ref=wilson")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@ReferalActivity, "Link Berhasil Disalin", Toast.LENGTH_LONG).show()
        }
    }

}