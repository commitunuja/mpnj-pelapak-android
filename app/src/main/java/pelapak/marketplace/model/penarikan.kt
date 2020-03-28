package pelapak.marketplace.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class penarikan {
    @SerializedName("value")
    @Expose
    var value: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("result")
    @Expose
    var result: ArrayList<r_penarikan>? = null
}