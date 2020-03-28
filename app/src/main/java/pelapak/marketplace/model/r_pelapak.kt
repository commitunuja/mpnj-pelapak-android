package pelapak.marketplace.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_pelapak {
    @SerializedName("id_pelapak")
    @Expose
    var id_pelapak: String? = null

    @SerializedName("nama_lengkap")
    @Expose
    var nama_lengkap: String? = null

    @SerializedName("nama_toko")
    @Expose
    var nama_toko: String? = null

    @SerializedName("alamat_toko")
    @Expose
    var alamat_toko: String? = null

    @SerializedName("alamat")
    @Expose
    var alamat: String? = null

    @SerializedName("kode_pos")
    @Expose
    var kode_pos: String? = null

    @SerializedName("nomor_hp")
    @Expose
    var nomor_hp: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("rating")
    @Expose
     var rating: String? = null

    @SerializedName("saldo")
    @Expose
     var saldo: String? = null

    @SerializedName("foto_profil")
    @Expose
     var foto_profil: String? = null


    @SerializedName("status_official")
    @Expose
    var status_official: String? = null

    @SerializedName("provinsi_id")
    @Expose
    var provinsi_id: String? = null

    @SerializedName("city_id")
    @Expose
    var city_id: String? = null

}
