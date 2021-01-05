package com.example.kurditing

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kurditing.model.SubCourse
import com.example.kurditing.utils.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment.iv_back
import kotlinx.android.synthetic.main.activity_payment.iv_poster
import kotlinx.android.synthetic.main.activity_payment.tv_harga


class PaymentActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        preferences = Preferences(this)

        tv_judul.text = intent.getStringExtra("judul")
        tv_owner.text = intent.getStringExtra("owner")
        tv_harga.text = intent.getStringExtra("harga")
        tv_jumlah_video.text = intent.getStringExtra("total_video") + " Video"
        Glide.with(this)
                .load(intent.getStringExtra("owner_poster"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_poster)

        tv_saldo.text = preferences.getValues("saldo").toString()

        iv_back.setOnClickListener(){
            finish()
        }
    }

    fun getCustomDialog(view: View) {
        var MyLayout = layoutInflater.inflate(R.layout.activity_custom_dialog,null)
        val myDialogBuilder = AlertDialog.Builder(this).apply {
            setView(MyLayout)
//            setTitle("Login")
        }
        var myDialog : AlertDialog = myDialogBuilder.create()
        var cancel = MyLayout.findViewById<ImageView>(R.id.iv_cancel)
        cancel.setOnClickListener(){
            myDialog.cancel()
        }
//        var ID = MyLayout.findViewById<EditText>(R.id.UserID)
//        var Pass = MyLayout.findViewById<EditText>(R.id.UserPass)
//        var BtnLogin = MyLayout.findViewById<Button>(R.id.login)
//        BtnLogin.setOnClickListener(){
//            if(ID.text.toString().equals("123") && Pass.text.toString().equals("123"))
//                Toast.makeText(this,"Login Sukses", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(this,"Login Gagal", Toast.LENGTH_SHORT).show()
//            myDialog.cancel()
//        }

        myDialog.show()
    }
}