package pelapak.marketplace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.poter.PLCMonitoring.apdater.rekeningAdapter
import kotlinx.android.synthetic.main.activity_rekening.*
import pelapak.marketplace.R
import pelapak.marketplace.persenter._rekening
import pelapak.marketplace.persenter.rekening
import pelapak.marketplace.model.rekening as tampil_rekening

class rekening : AppCompatActivity(),rekening.view, rekeningAdapter.AdapterCallback {
    lateinit var  persenter : _rekening
    var mAdapter: rekeningAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekening)
        persenter = _rekening()
        iv_back_rekening.setOnClickListener { finish() }
        persenter.tampil_rekening(this,this,this)
        persenter.tampil_rekening(this,this,this)
    }

    override fun tampil(rekening: tampil_rekening) {
        mAdapter = rekeningAdapter(this,rekening.result,this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.scrollToPositionWithOffset(0, 0)
        rv_tampil_rekening.setLayoutManager(linearLayoutManager)
        rv_tampil_rekening.adapter = mAdapter
        rv_tampil_rekening.smoothScrollToPosition(0)
    }

    override fun onRowAdapterClicked(position: Int) {

    }
}
