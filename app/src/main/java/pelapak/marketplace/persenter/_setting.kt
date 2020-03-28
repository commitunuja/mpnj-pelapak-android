package pelapak.marketplace.persenter


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pelapak.marketplace.MainActivity
import pelapak.marketplace.R
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.server.API
import pelapak.marketplace.server.APIClient
import pelapak.marketplace.utils.link
import pelapak.marketplace.utils.pesan


class _setting : setting.persenter{
    var disposable = CompositeDisposable()
    override fun profil(
        context: Context,
        tampil : setting.view,
        foto: ImageView,
        nama_pemilik: TextView,
        alamat_pemilik: TextView,
        telepon_pemilik: TextView,
        email_pemilik: TextView,
        nama_toko: TextView,
        alamat_toko: TextView,
        status_toko: TextView
    ) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .profil(sharedPreferences.getString("id","").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<pelapak>() {
                    override fun onSuccess(data: pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            tampil.tampil_profil(data)
                        }else{
                            pesan.toast(context, data.message.toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }

    override fun logout(context: Context, activity: Activity) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
        context.startActivity(Intent(context,MainActivity::class.java))
        (activity.finish())
    }

    override fun toko(context: Context, activity: Activity, nama: String, alamat: String, status: String) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        val pref = context.getSharedPreferences("app", Context.MODE_PRIVATE)

        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .edit_toko(sharedPreferences.getString("id","").toString(),nama,alamat,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<pelapak>() {
                    override fun onSuccess(data: pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            pref.edit().clear().commit()
                            activity.finish()
                        }else{
                            pesan.toast(context, data.message.toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }

    override fun pemilik(
        context: Context,
        activity: Activity,
        nama: String,
        provinsi_id: String,
        city_id: String,
        alamat: String,
        kode_pos: String,
        nomor_hp: String,
        email: String
    ) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .edit_pemilik(sharedPreferences.getString("id","").toString(),nama,provinsi_id,city_id,alamat,kode_pos,nomor_hp,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<pelapak>() {
                    override fun onSuccess(data: pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            activity.finish()
                        }else{
                            pesan.toast(context, data.message.toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }

}