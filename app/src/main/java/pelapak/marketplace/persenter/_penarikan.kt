package pelapak.marketplace.persenter

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pelapak.marketplace.model.rekening
import pelapak.marketplace.server.API
import pelapak.marketplace.server.APIClient
import pelapak.marketplace.utils.pesan
import pelapak.marketplace.persenter.penarikan.view as view_penarikan
import pelapak.marketplace.model.penarikan as model_penarikan
import  pelapak.marketplace.persenter.penarikan.view as view_rekening

class _penarikan : penarikan.persenter {
    var disposable = CompositeDisposable()

    override fun tampil(
        context: Context,
        activity: Activity,
        view: view_penarikan
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .penarikan_tampil(sharedPreferences.getString("id", "").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_penarikan>() {
                    override fun onSuccess(data: model_penarikan) {
                        progressDialog.dismiss()
                        view.tampil(data)
                    }
                    override fun onError(e: Throwable) {
                        progressDialog.dismiss()

                    }
                })
        )
    }

    override fun simpan(
        context: Context,
        activity: Activity,
        id_rekening: String,
        nominal: String,
        view: view_penarikan
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .penarikan_simpan(
                    sharedPreferences.getString("id", "").toString(),
                    id_rekening,
                    nominal
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_penarikan>() {
                    override fun onSuccess(data: model_penarikan) {
                        view.respon(data)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    override fun edit(
        context: Context,
        activity: Activity,
        id_penarikan: String,
        id_rekening: String,
        nominal: String,
        view: view_penarikan
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .penarikan_edit(
                    id_penarikan,
                    sharedPreferences.getString("id", "").toString(),
                    id_rekening,
                    nominal
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_penarikan>() {
                    override fun onSuccess(data: model_penarikan) {
                        view.respon(data)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    override fun hapus(
        context: Context, activity: Activity, id_penarikan: String,
        view: view_penarikan
    ) {
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .penarikan_hapus(
                    id_penarikan
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<model_penarikan>() {
                    override fun onSuccess(data: model_penarikan) {
                        view.respon(data)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    override fun tampil_rekening(
        context: Context,
        activity: Activity,
        view: view_rekening
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .rekening_tampil(sharedPreferences.getString("id", "").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<rekening>() {
                    override fun onSuccess(data: rekening) {
                        if (data.value.equals("1")) {
                            view.tampil_rekening(data)
                        } else {
                            pesan.toast(context, data.message.toString())
                        }
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }
}