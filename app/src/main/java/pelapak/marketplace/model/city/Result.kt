package pelapak.marketplace.model.city

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result(
    @field:Expose @field:SerializedName("city_id") var cityId: String,
    @field:Expose @field:SerializedName("province_id") var provinceId: String,
    @field:Expose @field:SerializedName("province") var province: String,
    @field:Expose @field:SerializedName("type") var type: String,
    @field:Expose @field:SerializedName("city_name") var cityName: String,
    @field:Expose @field:SerializedName("postal_code") var postalCode: String
)