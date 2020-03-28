package pelapak.marketplace.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_alamat.*
import kotlinx.android.synthetic.main.activity_daftar.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.persenter.pelapak
import pelapak.marketplace.utils.pesan

class daftar : AppCompatActivity() {
    lateinit var persenter: _pelapak
    lateinit var pref : SharedPreferences
    var status : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)
        persenter = _pelapak()
        pref = getSharedPreferences("app",Context.MODE_PRIVATE)
        bt_daftar_alamat.setOnClickListener { startActivity(Intent(this,alamat::class.java)) }
        iv_back_daftar.setOnClickListener {
            finish()
        }
        rb_daftar_status.setOnCheckedChangeListener({ group, checkedId ->
            if (checkedId == R.id.santri){
                status = "santri";
            }else{
                status = "official"
            }
        })
        bt_daftar.setOnClickListener {
            if (nama_pemilik.text.toString().isEmpty()){
                pesan.toast(this,"Nama Pemilik Kosong")
                nama_pemilik.requestFocus()

            }else if(telepon_pemilik.text.toString().isEmpty()){
                pesan.toast(this,"Telepon Pemilik Kosong")
                telepon_pemilik.requestFocus()
            }else if(et_daftar_alamat.text.toString().isEmpty()){
                pesan.toast(this,"Alamat Pemilik Kosong")
            }else if(nama_toko.text.toString().isEmpty()){
                pesan.toast(this,"Nama Toko Kosong")
                nama_toko.requestFocus()
            }else if(status == null){
                pesan.toast(this,"Status Kosong")
            }else if(alamat_toko.text.toString().isEmpty()){
                pesan.toast(this,"Alamat Toko Kosong")
                alamat_toko.requestFocus()
            }else if(username_pemilik.text.toString().isEmpty()){
                pesan.toast(this,"Username Kosong")
                username_pemilik.requestFocus()
            }else if(email_pemilik.text.toString().isEmpty()){
                pesan.toast(this,"Email Kosong")
                email_pemilik.requestFocus()
            }else if(password_awal.text.toString().isEmpty()){
                pesan.toast(this,"Password Kosong")
                password_awal.requestFocus()
            }else if(password_ulang.text.toString().isEmpty()){
                pesan.toast(this,"Password Ulang Kosong")
                password_ulang.requestFocus()
            }else if(password_awal.text.toString().equals(password_ulang.text.toString())){
                    persenter.daftar(
                     this,this,
                        username_pemilik.text.toString(),
                        password_awal.text.toString(),
                        status.toString(),
                        nama_pemilik.text.toString(),
                        nama_toko.text.toString(),
                        alamat_toko.text.toString(),
                        pref.getString("id_provinsi","").toString(),
                        pref.getString("id_kota","").toString(),
                        et_daftar_alamat.text.toString(),
                        pref.getString("kodepos","").toString(),
                        telepon_pemilik.text.toString(),
                        email_pemilik.text.toString()
                    )
            }else{
                pesan.toast(this,"Password tidak sama")

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }
    override fun onResume() {
        super.onResume()
        val cek_alamat = pref.getBoolean("tambah_alamat",false)
        Log.e("dddddddddddddddddddd",cek_alamat.toString())
        if(cek_alamat){
            cc_alamat.visibility = View.VISIBLE
            et_daftar_alamat.setText(
                pref.getString("provinsi","")+" "+
                pref.getString("kota","")+" "+
                pref.getString("alamat","")+" "+
                pref.getString("kodepos","")
            )

        }
    }
}
