package com.example.kurditing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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