package pelapak.marketplace.model.city_id

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemCity {
    @SerializedName("rajaongkir")
    @Expose
    lateinit var rajaongkir: Rajaongkir
}