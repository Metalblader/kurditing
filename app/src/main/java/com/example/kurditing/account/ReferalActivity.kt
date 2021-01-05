package com.example.kurditing.account

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kurditing.DescriptionActivity
import com.example.kurditing.R
import com.example.kurditing.home.dashboard.BestSellerAdapter
import com.example.kurditing.home.dashboard.PopularAdapter
import com.example.kurditing.model.Course
import com.example.kurditing.model.Referal
import com.example.kurditing.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_referal.*
import kotlinx.android.synthetic.main.activity_referal.btn_ambil_kelas
import kotlinx.android.synthetic.main.activity_terms.iv_back
import kotlinx.android.synthetic.main.fragment_home.*


class ReferalActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference

    private var dataList = ArrayList<Referal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referal)

        preferences = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance().getReference("user")
                .child(preferences.getValues("uid").toString())

        iv_back.setOnClickListener(){
            finish();
        }
        btn_ambil_kelas.setOnClickListener(){
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

        rv_referal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ReferalActivity, ""+databaseError.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    var referal = getdataSnapshot.getValue(Referal::class.java)
                    dataList.add(referal!!)
                }

                rv_referal.adapter = ReferalAdapter(dataList){
                }
            }

        })
    }

}