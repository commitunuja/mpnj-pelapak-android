package pelapak.marketplace.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.costume_rekening.view.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import pelapak.marketplace.R
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.persenter._beranda
import pelapak.marketplace.persenter.beranda
import pelapak.marketplace.utils.link
import pelapak.marketplace.utils.pesan
import pelapak.marketplace.view.penarikan
import pelapak.marketplace.view.rekening


class beranda : Fragment(), beranda.view {
    lateinit var persenter: _beranda
    lateinit var foto : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_beranda, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        persenter = _beranda()
        beranda_foto.setOnClickListener {
            activity?.let { it1 -> pesan.alert_foto(it1, foto) }
        }
        beranda_rekening.setOnClickListener {
            startActivity(Intent(activity,rekening::class.java))

        }
        beranda_penarikan.setOnClickListener {
            startActivity(Intent(activity,penarikan::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            persenter.profil(
                it,
                this,
                beranda_foto,
                beranda_toko,
                beranda_status,
                beranda_saldo,
                beranda_ratting,
                beranda_nilai_ratting

            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        persenter.disposable.isDisposed
    }

    override fun tampil_profil(pelapak: pelapak) {
        beranda_toko.text = pelapak.result.nama_toko
        beranda_status.text = pelapak.result.status_official
        if (pelapak.result.saldo == null) {
            beranda_saldo.text = "0"

        } else {
            beranda_saldo.text = pelapak.result.saldo

        }

        if (pelapak.result.foto_profil == null) {
            activity?.let { pesan.foto(it) }
        } else {
            foto = pelapak.result.foto_profil!!
            activity?.let {
                Glide.with(it)
                    .load(link.foto_profil + pelapak.result.foto_profil)
                    .placeholder(R.drawable.ic_user)
                    .into(beranda_foto)
            }
        }
        if (pelapak.result.rating == null) {

        } else {
            beranda_ratting.rating = pelapak.result.rating!!.toFloat()
            val stars = beranda_ratting.getProgressDrawable() as LayerDrawable
            stars.getDrawable(2)
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            stars.getDrawable(0)
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            stars.getDrawable(1)
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            beranda_nilai_ratting.text = "101010"
        }
    }
}

