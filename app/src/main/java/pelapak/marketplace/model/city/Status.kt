package pelapak.marketplace.model.city

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Status {
    @SerializedName("code")
    @Expose
    lateinit var  code: String
    @SerializedName("description")
    @Expose
    lateinit var  description: String

}