package com.example.kurditing

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kurditing.model.Course
import com.example.kurditing.model.Detail
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.activity_description.btn_ambil_kelas
import kotlinx.android.synthetic.main.activity_description.tv_harga
import kotlinx.android.synthetic.main.activity_payment.*

class DescriptionActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Detail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val data = intent.getParcelableExtra<Course>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Course")
//                .child(data?.judul.toString())

        tv_title.text = data?.judul
        tv_desc.text = data?.desc
        tv_harga.text = "IDR "+data?.harga
        tv_rating.text = data?.rating
        tv_owner_poster.text = data?.owner


        Glide.with(this)
                .load(data?.poster)
                .into(iv_poster)

        btn_ambil_kelas.setOnClickListener(){
            var intent = Intent(this@DescriptionActivity,PaymentActivity::class.java)
            intent.putExtra("judul",tv_title.text.toString())
            intent.putExtra("owner",tv_owner_poster.text.toString())
            intent.putExtra("harga", tv_harga.text.toString())
            startActivity(intent)
        }

        btn_nonton_cuplikan.setOnClickListener(){
            var intent = Intent(this@DescriptionActivity, VideoPlayingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DescriptionActivity,""+error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(getdataSnapshot in snapshot.children){
                    var Course = getdataSnapshot.getValue(Detail::class.java)
                    dataList.add(Course!!)
                }
            }
        })
    }

    fun backHome(view: View) {
        finish();
    }


}