package pelapak.marketplace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_verfikasi_akses.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.utils.pesan

class verfikasi_akses : AppCompatActivity() {
    lateinit var status : String
    lateinit var persenter: _pelapak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verfikasi_akses)
        persenter = _pelapak()
        iv_back_verfikasi.setOnClickListener { finish() }
        bt_telepon.setOnClickListener {
            cek_email.visibility = GONE
            cek_telepon.visibility = VISIBLE
            bt_cek.visibility = VISIBLE
            et_email_akses.setText("")
            et_telepon_akses.setText("")
            status = "1";
        }
        bt_email.setOnClickListener {
            cek_telepon.visibility = GONE
            cek_email.visibility = VISIBLE
            bt_cek.visibility = VISIBLE
            et_email_akses.setText("")
            et_telepon_akses.setText("")
            status = "2";
        }

        bt_cek.setOnClickListener {
            if (status == "1"){
                if (et_telepon_akses.text.toString().isEmpty()){
                    pesan.toast(this,"Telepon Kosong")
                }else{
                    persenter.cek(this,this,et_telepon_akses.text.toString())
                }
            }else if(status == "2"){
                if (et_email_akses.text.toString().isEmpty()){
                    pesan.toast(this,"Email Kosong")
                }else{
                    persenter.cek(this,this,et_email_akses.text.toString())
                }
            }
        }
    }
}
