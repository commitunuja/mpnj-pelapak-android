package pelapak.marketplace.persenter


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pelapak.marketplace.model.rekening as tampil_rekening
import pelapak.marketplace.server.API
import pelapak.marketplace.server.APIClient
import pelapak.marketplace.utils.pesan

class _rekening : rekening.persenter{
    var disposable = CompositeDisposable()
    override fun tampil_rekening(
        context: Context,
        activity: Activity,
        view: rekening.view
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        var progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .rekening_tampil(sharedPreferences.getString("id","").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<tampil_rekening>() {
                    override fun onSuccess(data: tampil_rekening) {
                        progressDialog.dismiss()
                        if (data.value.equals("1")){
                          view.tampil(data)
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