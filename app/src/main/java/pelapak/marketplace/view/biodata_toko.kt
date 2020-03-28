package pelapak.marketplace.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_biodata_toko.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._setting

class biodata_toko : AppCompatActivity() {
    lateinit var persenter: _setting
    lateinit var status : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata_toko)
        persenter = _setting()
        et_biodata_toko_nama.setText(intent.getStringExtra("nama").toString())
        et_biodata_toko_alamat.setText(intent.getStringExtra("alamat").toString())
        if (intent.getStringExtra("status").toString().equals("santri")){
            rb_biodata_toko_santri.isChecked = true
            status = "santri"
        }else{
            rb_biodata_toko_official.isChecked = true
            status = "official"
        }
        rb_biodata_toko_status.setOnCheckedChangeListener({ group, checkedId ->
            if (checkedId == R.id.rb_biodata_toko_santri){
                status = "santri";
            }else{
                status = "official"
            }
        })
        iv_back_edit_biodata.setOnClickListener { finish() }
        bt_biodata_toko_edit.setOnClickListener {
            persenter.toko(this,this,et_biodata_toko_nama.text.toString(),et_biodata_toko_alamat.text.toString(),status)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }
}
