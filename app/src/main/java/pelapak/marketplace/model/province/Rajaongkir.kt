package pelapak.marketplace.model.province

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rajaongkir {
    @SerializedName("query")
    @Expose
    lateinit var  query: List<Any>
    @SerializedName("status")
    @Expose
    lateinit var  status: Status
    @SerializedName("results")
    @Expose
    lateinit var  results: List<Result>

}