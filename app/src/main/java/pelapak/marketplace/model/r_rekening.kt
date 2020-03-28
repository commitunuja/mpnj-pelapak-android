package pelapak.marketplace.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class r_rekening {

    @SerializedName("id_rekening")
    @Expose
    var id_rekening: String? = null

    @SerializedName("nama_bank")
    @Expose
    var nama_bank: String? = null

    @SerializedName("nomor_rekening")
    @Expose
    var nomor_rekening: String? = null

    @SerializedName("atas_nama")
    @Expose
    var atas_nama: String? = null
}
