package pelapak.marketplace.persenter

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pelapak.marketplace.server.API
import pelapak.marketplace.server.APIClient
import pelapak.marketplace.utils.FileCompressor
import pelapak.marketplace.utils.pesan
import pelapak.marketplace.view.lupa_password
import pelapak.marketplace.view.menu
import java.io.File
import java.util.*
import java.util.Locale.getDefault
import pelapak.marketplace.model.pelapak as model_pelapak


class _pelapak : pelapak.persenter{
    var disposable = CompositeDisposable()
    override fun daftar(
        context: Context,
        activity: Activity,
        username: String,
        password: String,
        status_official: String,
        nama_lengkap: String,
        nama_toko: String,
        alamat_toko : String,
        provinsi_id: String,
        city_id: String,
        alamat: String,
        kode_pos: String,
        nomor_hp: String,
        email: String
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        val pref = context.getSharedPreferences("app", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .daftar(username,password,status_official,nama_lengkap,nama_toko,alamat_toko,provinsi_id,city_id,alamat,kode_pos,nomor_hp,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_pelapak>() {
                    override fun onSuccess(data: model_pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("status", true)
                            editor.putString("id", data.result.id_pelapak)
                            editor.commit()
                            pref.edit().clear().commit()
                            val intent = Intent(context,menu::class.java)
                            intent.addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            activity.startActivity(intent)
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

    override fun login(context: Context, activity: Activity, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_pelapak>() {
                    override fun onSuccess(data: model_pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("status", true)
                            editor.putString("id", data.result.id_pelapak)
                            editor.commit()
                            val intent = Intent(context,menu::class.java)
                            activity.startActivity(intent)
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

    override fun cek(context: Context, activity: Activity, data: String) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .cek(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_pelapak>() {
                    override fun onSuccess(data: model_pelapak) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                            val intent = Intent(context,lupa_password::class.java)
                            intent.putExtra("id",data.result.id_pelapak)
                            activity.startActivity(intent)
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

    override fun password(
        context: Context,
        activity: Activity,
        id_pelapak: String,
        password: String
    ) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .password(id_pelapak, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_pelapak>() {
                    override fun onSuccess(data: model_pelapak) {
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

    override fun upload(context: Context, activity: Activity, file_ok: File, foto: ImageView) {
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        val id = RequestBody.create("text/plain".toMediaTypeOrNull(),sharedPreferences.getString("id","").toString())

        val mFile = RequestBody.create("image/*".toMediaTypeOrNull(), file_ok)
        val fileToUpload = MultipartBody.Part.createFormData("file", file_ok.getName(), mFile)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .upload_foto(id,fileToUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_pelapak>() {
                    override fun onSuccess(data: model_pelapak) {
                        progressDialog.dismiss()
                        Glide.with(context)
                            .load(data.result.foto_profil)
                            .into(foto)
                    }
                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()
                    }
                })
        )

    }

}