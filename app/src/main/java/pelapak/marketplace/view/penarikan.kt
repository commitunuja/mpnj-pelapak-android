package pelapak.marketplace.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import id.poter.PLCMonitoring.apdater.penarikanAdapter
import id.poter.PLCMonitoring.apdater.rekeningAdapter
import kotlinx.android.synthetic.main.activity_penarikan.*
import kotlinx.android.synthetic.main.costume_penarikan.*
import kotlinx.android.synthetic.main.costume_penarikan.view.*
import pelapak.marketplace.R
import pelapak.marketplace.model.city_id.result
import pelapak.marketplace.model.r_penarikan
import pelapak.marketplace.model.rekening
import pelapak.marketplace.persenter._penarikan
import pelapak.marketplace.persenter.penarikan
import pelapak.marketplace.utils.pesan

class penarikan : AppCompatActivity(), penarikan.view, penarikanAdapter.AdapterCallback {
    lateinit var persenter: _penarikan
    lateinit var aa: ArrayAdapter<String>
    var mAdapter: penarikanAdapter? = null
    var array_nama: ArrayList<String> = ArrayList()
    var array_id: ArrayList<String> = ArrayList()
    var id_rekenng: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penarikan)
        persenter = _penarikan()
        array_nama.add("Pilih Rekening")
        persenter.tampil(this, this, this)
        persenter.tampil_rekening(this, this, this)

        iv_penarikan.setOnClickListener { finish() }
        bt_pengajuan.setOnClickListener {
            val dialog = Dialog(this, R.style.MyDialog)
            val view: View = LayoutInflater.from(this).inflate(R.layout.costume_penarikan, null)
            val params = dialog.window!!.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.setContentView(view)
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(true)
            view.cp_close.setOnClickListener {
                dialog.dismiss()
            }
            view.cpp_rekening.adapter = aa
            view.cpp_rekening.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent_: AdapterView<*>?,
                    view_: View?,
                    position_: Int,
                    id_: Long
                ) {
                    if (view.cpp_rekening.getSelectedItem().equals("Pilih Rekening")) {

                    } else {
                        id_rekenng = array_id.get(position_ - 1)
                        Log.e("okkkkk", array_id.get(position_ - 1))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            view.cpp_simpan.setOnClickListener {
                if (id_rekenng == null) {
                    pesan.toast(this, "pilih nomor rekening")

                } else if (view.cpp_nominal.text.toString().isEmpty()) {
                    pesan.toast(this, "nominal tidak boleh kosong")
                } else {
                    persenter.simpan(
                        this,
                        this,
                        id_rekenng!!,
                        view.cpp_nominal.text.toString(),
                        this
                    )
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }

    override fun tampil(penarikan: pelapak.marketplace.model.penarikan) {
        if (penarikan.value.equals("1")) {
            mAdapter = penarikanAdapter(this, penarikan.result!!, this)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.scrollToPositionWithOffset(0, 0)
            rv_tampil_penarikan.setLayoutManager(linearLayoutManager)
            rv_tampil_penarikan.adapter = mAdapter
            rv_tampil_penarikan.smoothScrollToPosition(0)
            rv_tampil_penarikan.visibility = View.VISIBLE
        } else {
            rv_tampil_penarikan.visibility = View.GONE
        }

    }

    override fun respon(penarikan: pelapak.marketplace.model.penarikan) {
        if (penarikan.value.equals("1")) {
            persenter.tampil(this, this, this)
        } else {

        }
    }

    override fun tampil_rekening(rekening: rekening) {
        for (a in 0..rekening.result.size - 1) {
            array_nama.add(rekening.result[a].atas_nama + "\n" + rekening.result[a].nomor_rekening + " - " + rekening.result[a].nama_bank)
            array_id.add(rekening.result[a].id_rekening.toString())
        }
        aa = ArrayAdapter(this@penarikan, android.R.layout.simple_spinner_item, array_nama)
        aa.setDropDownViewResource(android.R.layout.simple_selectable_list_item)


    }

    override fun onRowAdapterClicked(position: r_penarikan) {

        if (position.status.equals("pending")){
            val pesan = AlertDialog.Builder(this)
            pesan.setTitle("Peringatan")
            pesan.setMessage("Apakah anda yakin ubah data ini?")
            pesan.setPositiveButton("Edit Data",{dialog, which ->
                val dialog_ = Dialog(this, R.style.MyDialog)
                val view: View = LayoutInflater.from(this).inflate(R.layout.costume_penarikan, null)
                val params = dialog_.window!!.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                dialog_.setContentView(view)
                dialog_.window!!.setGravity(Gravity.CENTER)
                dialog_.setCancelable(true)
                view.cp_close.setOnClickListener {
                    dialog_.dismiss()
                }
                view.cpp_rekening.adapter = aa
                id_rekenng = position.id_rekening
                view.cpp_rekening.setSelection(position.id_rekening!!.toInt())
                view.cpp_nominal.setText(position.nominal)
                view.cpp_rekening.setOnItemSelectedListener(object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent_: AdapterView<*>?,
                        view_: View?,
                        position_: Int,
                        id_: Long
                    ) {
                        if (view.cpp_rekening.getSelectedItem().equals("Pilih Rekening")) {

                        } else {
                            id_rekenng = array_id.get(position_ - 1)
                            Log.e("okkkkk", array_id.get(position_ - 1))
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                })
                dialog_.show()
            })
            pesan.setNegativeButton("Cancel",{dialog, which ->
                dialog.dismiss()
            })
            pesan.setNeutralButton("Hapus Data",{dialog, which ->
                dialog.dismiss()
                persenter.hapus(this,this,position.id_withdraw.toString(),this)
            })
            val dialog = pesan.create()
            dialog.show()
        }else{
            pesan.no_edit(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }

}
