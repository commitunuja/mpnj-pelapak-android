package pelapak.marketplace

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.utils.pesan
import pelapak.marketplace.view.daftar
import pelapak.marketplace.view.menu
import pelapak.marketplace.view.verfikasi_akses

class MainActivity : AppCompatActivity() {
    lateinit var persenter: _pelapak
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        persenter = _pelapak()

        tv_daftar.setOnClickListener {
            startActivity(Intent(this,daftar::class.java))
        }
        bt_login.setOnClickListener {
            if(et_username.text.toString().isEmpty()){
                pesan.toast(this,"Username Kosong")
                et_username.requestFocus()
            }else if(et_password.text.toString().isEmpty()){
                pesan.toast(this,"Password Kosong")
                et_username.requestFocus()
            }else{
                persenter.login(this,this,et_username.text.toString(),et_password.text.toString())
            }
        }
        tv_lupa_password.setOnClickListener {
            startActivity(Intent(this,verfikasi_akses::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("app", Context.MODE_PRIVATE)
        pref.edit().clear().commit()
        val sharedPreferences = getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        val data = sharedPreferences.getBoolean("status",false)
        if(data){
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            finish()
        }

    }
}
