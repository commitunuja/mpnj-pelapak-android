package pelapak.marketplace.persenter

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import pelapak.marketplace.model.pelapak

interface beranda {

    interface persenter{
        fun profil(context: Context, tampil : view,
                   foto: ImageView,
                   nama_toko: TextView,
                   status: TextView,
                   saldo: TextView,
                   ratting : RatingBar,
                   nilai : TextView
        )
    }
    interface view{
        fun tampil_profil(pelapak: pelapak)
    }
}