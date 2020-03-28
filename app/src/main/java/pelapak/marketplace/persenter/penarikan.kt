package pelapak.marketplace.persenter

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.model.penarikan
import pelapak.marketplace.model.rekening

interface penarikan {

    interface persenter {
        fun tampil(context: Context,activity: Activity,view: view)
        fun simpan(context: Context,activity: Activity, id_rekening : String,nominal : String,view: view)
        fun edit(context: Context,activity: Activity, id_penarikan : String,id_rekening : String,nominal : String,view: view)
        fun hapus(context: Context,activity: Activity, id_penarikan : String,view: view)

        fun tampil_rekening(context: Context,activity: Activity,view: view)

    }
    interface view {
        fun  tampil(penarikan: penarikan)
        fun  respon(penarikan: penarikan)
        fun  tampil_rekening(rekening: rekening)
    }
}