package pelapak.marketplace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lupa_password.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.utils.pesan

class lupa_password : AppCompatActivity() {
    lateinit var persenter: _pelapak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lupa_password)
        persenter = _pelapak()
        iv_back_lupa_password.setOnClickListener {
            finish()
        }
        bt_lupa_password.setOnClickListener {
            if(et_lupa_password_password.text.toString().isEmpty()){
                pesan.toast(this,"Password Kosong")
                et_lupa_password_password.requestFocus()
            }else if(et_lupa_password_ulang.text.toString().isEmpty()){
                pesan.toast(this,"Password Ulang Kosong")
                et_lupa_password_password.requestFocus()
            }else if(et_lupa_password_ulang.text.toString().equals(et_lupa_password_password.text.toString())){
                persenter.password(this,this,intent.getStringExtra("id"),et_lupa_password_password.text.toString())
            }else{
                pesan.toast(this,"Password tidak sama")
            }
        }
    }
}
