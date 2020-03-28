package pelapak.marketplace.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_biodata_pemilik.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._setting

class biodata_pemilik : AppCompatActivity() {
    lateinit var pref : SharedPreferences
    lateinit var presenter : _setting
    lateinit var provinsi : String
    lateinit var kota : String
    lateinit var kode_pos : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata_pemilik)
        presenter = _setting()
        pref =getSharedPreferences("app", Context.MODE_PRIVATE)
        iv_back_pemilik.setOnClickListener {
            pref.edit().clear().commit()
            finish()
        }
        et_biodata_pemilik_nama.setText( intent.getStringExtra("nama").toString())
        et_biodata_pemilik_alamat.setText( intent.getStringExtra("alamat").toString())
        et_biodata_pemilik_email.setText( intent.getStringExtra("email").toString())
        et_biodata_pemilik_telepon.setText( intent.getStringExtra("telepon").toString())
        bt_biodata_pemilik_alamat.setOnClickListener { startActivity(Intent(this,alamat::class.java)) }
        bt_biodata_pemilik_edit.setOnClickListener {
            presenter.pemilik(this,this,et_biodata_pemilik_nama.text.toString(),provinsi,kota,
                et_biodata_pemilik_alamat.text.toString(),kode_pos,et_biodata_pemilik_telepon.text.toString(),et_biodata_pemilik_email.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposable.isDisposed

    }

    override fun onBackPressed() {
        super.onBackPressed()
        pref.edit().clear().commit()
    }
    override fun onResume() {
        super.onResume()
        val cek_alamat = pref.getBoolean("tambah_alamat",false)
        if(cek_alamat){
            et_biodata_pemilik_alamat.setText(
                pref.getString("provinsi","")+" "+
                        pref.getString("kota","")+" "+
                        pref.getString("alamat","")+" "+
                        pref.getString("kodepos","")
            )
        }
        kode_pos = pref.getString("kodepos",intent.getStringExtra("kodepos").toString()).toString()
        provinsi = pref.getString("id_provinsi",intent.getStringExtra("provinsi").toString()).toString()
        kota = pref.getString("id_kota",intent.getStringExtra("kota").toString()).toString()
    }
}
