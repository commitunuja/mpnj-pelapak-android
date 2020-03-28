package pelapak.marketplace.persenter

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import java.io.File

interface pelapak {

    interface persenter{
        fun daftar(context: Context,activity: Activity,username : String,password : String,
                   status_official : String,nama_lengkap : String,nama_toko : String, alamat_toko : String,
                   provinsi_id : String,city_id : String,alamat : String,kode_pos : String,
                   nomor_hp : String,email : String)

        fun login(context: Context,activity: Activity,username : String,password : String)
        fun cek(context: Context,activity: Activity,data : String)
        fun password(context: Context,activity: Activity,id_pelapak : String, password : String)
        fun upload(context: Context, activity: Activity, file_ok: File, foto : ImageView)
    }
}