package pelapak.marketplace.model.city_id

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rajaongkir {
    @SerializedName("query")
    @Expose
    lateinit var  query: Query
    @SerializedName("status")
    @Expose
    lateinit var  status: Status
    @SerializedName("results")
    @Expose
    lateinit var  results: result

}