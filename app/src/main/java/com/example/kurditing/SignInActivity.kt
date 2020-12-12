package com.example.kurditing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val akun = tv_akun
        var ss = SpannableString(akun.text)
        var fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPurpleDark))

        ss.setSpan(fcs, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val intent = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.color = ContextCompat.getColor(applicationContext, R.color.colorPurpleDark)
                textPaint.isUnderlineText = false
            }
        }

        ss.setSpan(clickableSpan, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        akun.text = ss
        akun.movementMethod = LinkMovementMethod.getInstance()
    }
}