package pelapak.marketplace.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_alamat.*
import pelapak.marketplace.R
import pelapak.marketplace.model.city.ItemCity
import pelapak.marketplace.model.city.Result
import pelapak.marketplace.model.province.ItemProvince
import pelapak.marketplace.model.city_id.ItemCity as item_id

import pelapak.marketplace.server.contanst
import pelapak.marketplace.server.raja_ongkir
import pelapak.marketplace.utils.pesan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class alamat : AppCompatActivity() {
    var arrayProv: ArrayList<String> = ArrayList()
    var listID_prov: ArrayList<String> = ArrayList()
    var arrayKota: ArrayList<String> = ArrayList()
    var listID_Kota: ArrayList<String> = ArrayList()
    var id_provinsi: String? = null
    var id_kota: String? = null
    var provinsi: String? = null
    var kota: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alamat)
        provinsi()
        arrayProv.add("Pilih Provinsi")
        arrayKota.add("Pilih Kabupaten / Kota")
        iv_back_alamat.setOnClickListener { finish() }
        sp_provinsi.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (sp_provinsi.getSelectedItem().equals("Pilih Provinsi")) {

                } else {
                    arrayKota.clear()
                    listID_Kota.clear()
                    getCity(listID_prov.get(position - 1));
                    Log.e("okkkkk", listID_prov.get(position - 1))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        sp_kota.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (sp_kota.getSelectedItem().equals("Pilih Kabupaten / Kota")) {

                } else {
                    getCityid(listID_Kota.get(position))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        bt_alamat_simpan.setOnClickListener {
            if (id_provinsi == null) {
                pesan.toast(this, "Pilih Provinsisi")
            } else if (id_kota == null) {
                pesan.toast(this, "Pilih Kabupaten / Kota")

            } else if (et_alamat_alamat.text.toString().isEmpty()) {
                pesan.toast(this, "Alamat Kosong")

            } else if (et_alamat_kode_pos.text.toString().isEmpty()) {
                pesan.toast(this, "Kodepos Kosong")

            } else {
                val sharedprefe = getSharedPreferences("app",Context.MODE_PRIVATE)
                var input = sharedprefe.edit()
                input.putBoolean("tambah_alamat",true)
                input.putString("id_provinsi",id_provinsi)
                input.putString("id_kota",id_kota)
                input.putString("provinsi",provinsi)
                input.putString("kota",kota)
                input.putString("alamat",et_alamat_alamat.text.toString())
                input.putString("kodepos",et_alamat_kode_pos.text.toString())
                input.commit()
                finish()
            }
        }

    }

    fun provinsi() {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(contanst.URL_RAJAONGKIR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: raja_ongkir = retrofit.create(raja_ongkir::class.java)
        val call: Call<ItemProvince> = service.getProvince()

        call.enqueue(object : Callback<ItemProvince> {
            override fun onResponse(call: Call<ItemProvince>, response: Response<ItemProvince>) {
                if (response.isSuccessful()) {
                    val count_data: Int = response.body()!!.rajaongkir.results.size
                    for (a in 0..count_data - 1) {
                        arrayProv.add(response.body()!!.rajaongkir.results[a].province)
                        listID_prov.add(response.body()!!.rajaongkir.results[a].provinceId)
                    }
                    val aa =
                        ArrayAdapter(this@alamat, android.R.layout.simple_spinner_item, arrayProv)
                    aa.setDropDownViewResource(android.R.layout.simple_selectable_list_item)

                    sp_provinsi.adapter = aa
                } else {
                    val error = "Error Retrive DataProfil from Server !!!"
                    Toast.makeText(this@alamat, error, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ItemProvince>, t: Throwable) {

            }
        })
    }

    fun getCity(id_province: String?) {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(contanst.URL_RAJAONGKIR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: raja_ongkir = retrofit.create(raja_ongkir::class.java)
        val call: Call<ItemCity> = service.getCity(id_province)
        call.enqueue(object : Callback<ItemCity> {
            override fun onResponse(
                call: Call<ItemCity>,
                response: Response<ItemCity>
            ) {
                if (response.isSuccessful) {

                    val count_data = response.body()!!.rajaongkir.results.size
                    for (a in 0..count_data - 1) {
                        arrayKota.add(
                            response.body()!!.rajaongkir.results[a].type + " " + response.body()!!.rajaongkir.results[a].cityName
                        )
                        listID_Kota.add(response.body()!!.rajaongkir.results[a].cityId)

                    }
                    val aa = ArrayAdapter(
                        this@alamat,
                        android.R.layout.simple_selectable_list_item,
                        arrayKota
                    )
                    sp_kota.adapter = aa
                } else {
                    val error = "Error Retrive DataProfil from Server !!!"
                    Toast.makeText(this@alamat, error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ItemCity>, t: Throwable) {
                Toast.makeText(this@alamat, "Message : Error " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getCityid(id_city: String?) {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(contanst.URL_RAJAONGKIR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: raja_ongkir = retrofit.create(raja_ongkir::class.java)
        val call: Call<item_id> = service.getCityid(id_city)
        call.enqueue(object : Callback<item_id> {
            override fun onResponse(
                call: Call<item_id>,
                response: Response<item_id>
            ) {
                if (response.isSuccessful) {
                    id_provinsi = response.body()!!.rajaongkir.results.province_id
                    id_kota = response.body()!!.rajaongkir.results.city_id
                    provinsi = response.body()!!.rajaongkir.results.province
                    kota = response.body()!!.rajaongkir.results.city_name

                } else {
                    val error = "Error Retrive DataProfil from Server !!!"
                    Toast.makeText(this@alamat, error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<item_id>, t: Throwable) {
                Toast.makeText(this@alamat, "Message : Error " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}
