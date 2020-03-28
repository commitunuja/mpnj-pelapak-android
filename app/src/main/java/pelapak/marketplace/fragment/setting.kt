package pelapak.marketplace.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_setting.*
import pelapak.marketplace.R
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.persenter._setting
import pelapak.marketplace.persenter.setting
import pelapak.marketplace.utils.link
import pelapak.marketplace.utils.pesan
import pelapak.marketplace.view.biodata_pemilik
import pelapak.marketplace.view.biodata_toko
import pelapak.marketplace.view.foto as foto_activity

class setting : Fragment(),setting.view {
    lateinit var persenter: _setting
    lateinit var foto : String
    lateinit var provinsi : String
    lateinit var kota : String
    lateinit var kode_pos : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        persenter = _setting()
        setting_foto.setOnClickListener { activity?.let { it1 -> pesan.alert_foto(it1,foto) } }
        iv_setting_foto.setOnClickListener { startActivity(Intent(activity,foto_activity::class.java)) }
        iv_setting_toko.setOnClickListener {
            val i = Intent(activity,biodata_toko::class.java)
            i.putExtra("nama",tv_setting_nama_toko.text.toString())
            i.putExtra("alamat",tv_setting_alamat_toko.text.toString())
            i.putExtra("status",tv_setting_status_toko.text.toString())
            startActivity(i)
        }
        iv_setting_biodata.setOnClickListener {
            val q =Intent(activity,biodata_pemilik::class.java)
            q.putExtra("nama",tv_setting_nama_pemilik.text.toString())
            q.putExtra("email",tv_setting_email_pemilik.text.toString())
            q.putExtra("telepon",tv_setting_telepon_pemilik.text.toString())
            q.putExtra("alamat",tv_setting_alamat_pemilik.text.toString())
            q.putExtra("kota",kota)
            q.putExtra("provinsi",provinsi)
            q.putExtra("kodepos",kode_pos)
            startActivity(q)
        }

        ll_setting_logout.setOnClickListener {
            val pesan = AlertDialog.Builder(activity!!)
            pesan.setTitle("Info")
            pesan.setMessage("Apakah anda yakin keluar dari aplikasi ini?")
            pesan.setPositiveButton("Ya",{dialog, which ->
                activity?.let { it1 -> persenter.logout(it1,it1) }
            })
            pesan.setNegativeButton("Tidak",{dialog, which ->
                dialog.dismiss()
            })
            val dialog = pesan.create()
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            persenter.profil(
                it,this,setting_foto,tv_setting_nama_pemilik,tv_setting_alamat_pemilik,tv_setting_telepon_pemilik,
                tv_setting_email_pemilik,tv_setting_nama_toko,tv_setting_alamat_toko,tv_setting_status_toko)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }

    override fun tampil_profil(data:pelapak) {
        tv_setting_nama_pemilik.text = data.result.nama_lengkap
        tv_setting_alamat_pemilik.text = data.result.alamat
        tv_setting_telepon_pemilik.text = data.result.nomor_hp
        tv_setting_email_pemilik.text = data.result.email
        tv_setting_nama_toko.text = data.result.nama_toko
        tv_setting_alamat_toko.text = data.result.alamat_toko
        tv_setting_status_toko.text = data.result.status_official
        foto = data.result.foto_profil.toString()
        provinsi = data.result.provinsi_id.toString()
        kota = data.result.city_id.toString()
        kode_pos = data.result.kode_pos.toString()
        activity?.let {
            Glide.with(it)
                .load(link.foto_profil+data.result.foto_profil)
                .placeholder(R.drawable.ic_user)
                .into(setting_foto)
        }
    }
}

