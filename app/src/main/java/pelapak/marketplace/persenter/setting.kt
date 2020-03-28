package pelapak.marketplace.persenter

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import pelapak.marketplace.model.pelapak

interface setting {

    interface persenter{

        fun profil(context: Context,tampil : view,foto : ImageView,nama_pemilik : TextView,alamat_pemilik : TextView,
                   telepon_pemilik : TextView,email_pemilik : TextView,nama_toko : TextView,
                   alamat_toko : TextView,status_toko : TextView)
        fun logout(context: Context,activity: Activity)
        fun toko(context: Context,activity: Activity,nama : String,alamat : String, status : String)
        fun pemilik(context: Context,activity: Activity,nama : String, provinsi_id : String,city_id : String, alamat : String,
                    kode_pos : String,nomor_hp : String,email : String)
    }
    interface view{
        fun tampil_profil(pelapak: pelapak)
    }
}