package pelapak.marketplace.persenter


import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pelapak.marketplace.R
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.server.API
import pelapak.marketplace.server.APIClient
import pelapak.marketplace.utils.link
import pelapak.marketplace.utils.pesan


class _beranda : beranda.persenter{
    var disposable = CompositeDisposable()

    override fun profil(
        context: Context,
        tampil : beranda.view,
        foto: ImageView,
        nama_toko: TextView,
        status: TextView,
        saldo: TextView,
        ratting : RatingBar,
        nilai : TextView
    ) {
        val sharedPreferences = context.getSharedPreferences("aplikasi", Context.MODE_PRIVATE)
        disposable.add(
            APIClient.getClient(context)!!.create(API::class.java)
                .beranda_profil(sharedPreferences.getString("id","").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<pelapak>() {
                    override fun onSuccess(data: pelapak) {
                     if(data.value.equals("1")){
                         tampil.tampil_profil(data)
                     }else{
                         pesan.toast(context, data.message.toString())
                     }
                    }
                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

}