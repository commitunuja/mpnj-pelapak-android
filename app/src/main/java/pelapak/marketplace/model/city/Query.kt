package pelapak.marketplace.model.city

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Query {
    @SerializedName("province")
    @Expose
    lateinit var  province: String

}