package pelapak.marketplace.persenter

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.model.rekening

interface rekening {

    interface persenter {
        fun tampil_rekening(context: Context,activity: Activity,view: view)
    }
    interface view {
        fun  tampil(rekening: rekening)
    }
}