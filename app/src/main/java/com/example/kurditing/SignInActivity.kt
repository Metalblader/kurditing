package com.example.kurditing

import android.content.Intent
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kurditing.home.HomeActivity
import com.example.kurditing.model.User
import com.example.kurditing.utils.Preferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {
    // deklarasi variabel email dan password
    private lateinit var email: String
    private lateinit var password: String

    // deklarasi preferences
    private lateinit var preferences: Preferences
    // deklarasi firebase database reference
    private lateinit var database: DatabaseReference
    // deklarasi firebase auth
    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = "SignInActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // tampung reference firebase database ke dalam variabel database
        database = FirebaseDatabase.getInstance().reference
        // tampung instance firebase auth
        auth = FirebaseAuth.getInstance()
        // assign preference berdasarkan context aplikasi
        preferences = Preferences(this)

        setSpanText()

        if (preferences.getValues("status").equals("1")) {
            finishAffinity()

            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        // ketika btn_masuk diklik
        btn_masuk.setOnClickListener {
            // tampung teks email dan password ke dalam variabel
            email = et_email.text.toString()
            password = et_password.text.toString()

            // percabangan berikut hanya untuk pengecekan value dari edit text email dan password
            if (TextUtils.isEmpty(email)) {
                et_email.error = "Silahkan isi email Anda"
                et_email.requestFocus()
            }
            else if (!isEmailValid(email)) {
                et_email.error = "Email tidak valid"
                et_email.requestFocus()
            }
            else if (TextUtils.isEmpty(password)) {
                et_password.error = "Silahkan isi password Anda"
                et_password.requestFocus()
            }
            else {
                // jika sudah memenuhi syarat, maka lakukan sign in menggunakan firebase auth
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // jika berhasil, tampilkan toast berhasil login
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()

                        // deklarasi variabel user
                        var user: User?
                        // variabel uid menunjukkan uid user saat ini
                        val uid = auth.currentUser!!.uid
                        // tampung reference child bernilai uid dari child "user" dari database reference yang telah
                        // dideklarasikan
                        val uidRef = database.child("user").child(uid)

                        // tambahkan valueEventListener pada uidRef
                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // tampung value user dari firebase database
                                user = dataSnapshot.getValue(User::class.java)

                                // set atribut dari user ke dalam preferences
                                preferences.setValues("uid", user?.uid.toString())
                                preferences.setValues("nama", user?.nama.toString())
                                preferences.setValues("email", user?.email.toString())
                                preferences.setValues("password", user?.password.toString())
                                preferences.setValues("saldo", user?.saldo.toString())
                                preferences.setValues("status", "1")

                                // panggil finishAffinity() untuk mengakhiri activity ini dan activity di bawahnya
                                // pada stack
                                finishAffinity()

                                // pembuatan intent ke HomeActivity
                                val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                                // jalankan activity dengan argumen intent
                                startActivity(intent)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // ketika terjadi pembatalan, lakukan logging dan munculkan toast dengan pesan "Error"
                                Log.d(TAG, databaseError.message)
                                Toast.makeText(this@SignInActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        // jika task tidak sukses, maka tampilkan toast dengan pesan login failed
                        Toast.makeText(this, "Login Failed: " + (task.exception?.message
                                ?: "NULL"), Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

    }

    // fungsi utility untuk pengecekan email menggunakan class Patterns
    private fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

//    private fun login(email: String, password: String) {
//        database.child(email).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                val user = dataSnapshot.getValue(User::class.java)
//                if (user == null) {
//                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
//
//                } else {
//                    if (user.password.equals(iPassword)){
//                        Toast.makeText(this@SignInActivity, "Selamat Datang", Toast.LENGTH_LONG).show()
//
//                        preferences.setValues("nama", user.nama.toString())
//                        preferences.setValues("user", user.username.toString())
//                        preferences.setValues("url", user.url.toString())
//                        preferences.setValues("email", user.email.toString())
//                        preferences.setValues("saldo", user.saldo.toString())
//                        preferences.setValues("status", "1")
//
//                        finishAffinity()
//
//                        val intent = Intent(this@SignInActivity,
//                                HomeActivity::class.java)
//                        startActivity(intent)
//
//                    } else {
//                        Toast.makeText(this@SignInActivity, "Password Anda Salah", Toast.LENGTH_LONG).show()
//                    }
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@SignInActivity, ""+error.message, Toast.LENGTH_LONG).show()
//            }
//        })
//    }

    private fun setSpanText() {
        val akun = tv_akun
        var ss = SpannableString(akun.text)
        var fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPurpleDark))

        ss.setSpan(fcs, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val intent = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.color = ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPurpleDark
                )
                textPaint.isUnderlineText = false
            }
        }

        ss.setSpan(clickableSpan, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        akun.text = ss
        akun.movementMethod = LinkMovementMethod.getInstance()
    }


}