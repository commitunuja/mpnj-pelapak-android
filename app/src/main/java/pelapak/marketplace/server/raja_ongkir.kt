package pelapak.marketplace.server

import pelapak.marketplace.model.city.ItemCity
import pelapak.marketplace.model.city_id.ItemCity as item_id
import pelapak.marketplace.model.province.ItemProvince
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface raja_ongkir {

    // Province get from raja ongkir
    @GET("province")
    @Headers("key:1c082f667d455277ed87334b364c9ac3")
    fun getProvince(): Call<ItemProvince>
    //1c082f667d455277ed87334b364c9ac3 panggil di register

    //1c082f667d455277ed87334b364c9ac3 panggil di register
// City get from raja ongkir
    @GET("city")
    @Headers("key:1c082f667d455277ed87334b364c9ac3")
    fun getCity(@Query("province") province: String?): Call<ItemCity>

    @GET("city")
    @Headers("key:1c082f667d455277ed87334b364c9ac3")
    fun getCityid(@Query("id") province: String?): Call<item_id>
}
