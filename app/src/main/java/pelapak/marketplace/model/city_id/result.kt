package pelapak.marketplace.model.city_id

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class result {
    @SerializedName("city_id")
    @Expose
    lateinit var  city_id: String

    @SerializedName("province_id")
    @Expose
    lateinit var  province_id: String

    @SerializedName("province")
    @Expose
    lateinit var  province: String

    @SerializedName("type")
    @Expose
    lateinit var  type: String

    @SerializedName("city_name")
    @Expose
    lateinit var  city_name: String

    @SerializedName("postal_code")
    @Expose
    lateinit var  postal_code: String
}