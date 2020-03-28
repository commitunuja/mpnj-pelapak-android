package pelapak.marketplace.model.province

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result(
    @field:Expose @field:SerializedName("province_id") var provinceId: String, @field:Expose @field:SerializedName(
        "province"
    ) var province: String
)