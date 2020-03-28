package pelapak.marketplace.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pelapak.marketplace.R
import pelapak.marketplace.view.foto

object pesan {
    fun toast(context: Context, nama : String){
        Toast.makeText(context,nama,Toast.LENGTH_LONG).show()
    }
    fun foto(context: Context){
        val pesan = AlertDialog.Builder(context)
        pesan.setTitle("Peringatan")
        pesan.setMessage("Silakan tambahakan foto....")
        pesan.setCancelable(false)
        pesan.setPositiveButton("Ok",{dialog, which ->
            context.startActivity(Intent(context,foto::class.java))
        })
        val dialog = pesan.create()
        dialog.show()
    }
    fun  alert_foto (context: Context,gambar : String){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.image_zoom)
        val foto = dialog.findViewById<ImageView>(R.id.iz_gambar)
        context?.let {
            Glide.with(it)
                .load(link.foto_profil+gambar)
                .placeholder(R.drawable.ic_user)
                .into(foto)
        }
        dialog.show()

    }
    fun no_edit(context: Context){
        val pesan = AlertDialog.Builder(context)
        pesan.setTitle("Peringatan")
        pesan.setMessage("Mohon maaf data tidak bisa ubah...")
        pesan.setCancelable(false)
        pesan.setPositiveButton("Ok",{dialog, which ->
            dialog.dismiss()
        })
        val dialog = pesan.create()
        dialog.show()
    }
}