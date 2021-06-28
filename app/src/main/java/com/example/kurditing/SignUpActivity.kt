package com.example.kurditing

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.et_email
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import kotlinx.android.synthetic.main.activity_sign_up.tv_akun


class SignUpActivity : AppCompatActivity() {
    // deklarasi variabel nama, email, password, dan cPassword
    private lateinit var nama: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var cPassword: String

    // deklarasi preferences
    private lateinit var preferences: Preferences
    // deklarasi firebase database reference
    private lateinit var database: DatabaseReference
    // deklarasi firebase auth
    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = "SignUpActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // tampung reference firebase database ke dalam variabel database
        database = FirebaseDatabase.getInstance().reference
        // tampung instance firebase auth
        auth = FirebaseAuth.getInstance()
        // assign preference berdasarkan context aplikasi
        preferences = Preferences(this)

        setSpanText()

        // ketika btn_daftar diklik
        btn_daftar.setOnClickListener{
            // tampung teks nama, email, password, dan cPassword ke dalam variabel
            nama = et_nama.text.toString()
            email = et_email.text.toString()
            password = et_password.text.toString()
            cPassword = et_confirm_password.text.toString()

            // percabangan berikut hanya untuk pengecekan value dari edit text
            if(TextUtils.isEmpty(nama)) {
                et_nama.error = "Silahkan isi nama Anda"
                et_nama.requestFocus()
            }
            else if (TextUtils.isEmpty(email)) {
                et_nama.error = "Silahkan isi email Anda"
                et_nama.requestFocus()
            }
            else if (!isEmailValid(email)) {
                et_email.error = "Email tidak valid"
                et_email.requestFocus()
            }
            else if (TextUtils.isEmpty(password)) {
                et_password.error = "Silahkan isi password Anda"
                et_password.requestFocus()
            }
            else if (TextUtils.isEmpty(cPassword) || !password.equals(cPassword)) {
                et_confirm_password.error = "Isi password tidak sesuai"
                et_confirm_password.requestFocus()
            }
            else {
                // jika sudah memenuhi syarat, maka lakukan sign up menggunakan firebase auth
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // jika berhasil, tampilkan toast berhasil login
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()

                        // panggil fungsi onAuthSuccess
                        onAuthSuccess(task.result?.user)

                        // deklarasi variabel user
                        var user: User?
                        // variabel uid menunjukkan uid user saat ini
                        val uid = auth.currentUser!!.uid
                        // tampung reference child bernilai uid dari child "user" dari database reference yang telah
                        // dideklarasikan
                        val uidRef = database.child("user").child(uid)

                        // buat valueEventListener pada
                        val valueEventListener = object : ValueEventListener {
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
                                val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                                // jalankan activity dengan argumen intent
                                startActivity(intent)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // ketika terjadi pembatalan, lakukan logging pesan error
                                Log.d(TAG, databaseError.message)
                            }
                        }
                        // tambahkan valueEventListener pada uidRef
                        uidRef.addValueEventListener(valueEventListener)
                    } else {
                        // jika task tidak sukses, maka tampilkan toast dengan pesan login failed
                        Toast.makeText(this, "Registration Failed: " + (task.exception?.message
                                ?: "NULL"), Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    // fungsi onAuthSuccess yang memanggil fungsi writeNewUser dengan argumen uid, nama, email, password
    private fun onAuthSuccess(user: FirebaseUser?) {
        writeNewUser(user!!.uid, nama, email, password)
    }

    // fungsi writeNewUser berfungsi untuk menambahkan user pada firebase realtime database, yaitu
    // pada path "user"
    private fun writeNewUser(userId: String, nama: String, email: String, password: String) {
        val user = User()
        user.uid = userId
        user.nama = nama
        user.email = email
        user.password = password
        user.saldo = "0"

        database.child("user").child(userId).setValue(user)
    }

    // fungsi utility untuk pengecekan email menggunakan class Patterns
    private fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setSpanText() {
        val akun = tv_akun
        var ss = SpannableString(akun.text)
        var fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPurpleDark))

        ss.setSpan(fcs, 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val intent = Intent(applicationContext, SignInActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.color = ContextCompat.getColor(applicationContext, R.color.colorPurpleDark)
                textPaint.isUnderlineText = false
            }
        }

        ss.setSpan(clickableSpan, 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        akun.text = ss
        akun.movementMethod = LinkMovementMethod.getInstance()
    }
}