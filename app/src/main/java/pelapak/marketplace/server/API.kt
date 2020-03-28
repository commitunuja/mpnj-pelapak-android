package pelapak.marketplace.server

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pelapak.marketplace.model.pelapak
import pelapak.marketplace.model.penarikan
import pelapak.marketplace.model.rekening
import retrofit2.http.*

interface API {

    @FormUrlEncoded
    @POST("daftar")
    fun daftar(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("status_official") status_official: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nama_toko") nama_toko: String,
        @Field("alamat_toko") alamat_toko: String,
        @Field("provinsi_id") provinsi_id: String,
        @Field("city_id") city_id: String,
        @Field("alamat") alamat: String,
        @Field("kode_pos") kode_pos: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("email") email: String
    ): Single<pelapak>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Single<pelapak>

    @FormUrlEncoded
    @POST("cek")
    fun cek(@Field("data") data: String): Single<pelapak>

    @FormUrlEncoded
    @PUT("password/{id_pelapak}")
    fun password(
        @Path("id_pelapak") id_pelapak: String,
        @Field("password") password: String
    ): Single<pelapak>

    @GET("profil/beranda/{id_pelapak}")
    fun beranda_profil(@Path("id_pelapak") id_pelapak: String): Single<pelapak>

    @GET("profil/profil/{id_pelapak}")
    fun profil(@Path("id_pelapak") id_pelapak: String): Single<pelapak>

    @Multipart
    @POST("upload")
    fun upload_foto(@Part("id_pelapak") id_pelapak: RequestBody, @Part filename: MultipartBody.Part): Single<pelapak>

    @FormUrlEncoded
    @PUT("profil/toko/{id_pelapak}")
    fun edit_toko(
        @Path("id_pelapak") id_pelapak: String,
        @Field("nama_toko") nama_toko: String,
        @Field("alamat_toko") alamat_toko: String,
        @Field("status_official") status_official: String
    ): Single<pelapak>

    @FormUrlEncoded
    @PUT("profil/pemilik/{id_pelapak}")
    fun edit_pemilik(
        @Path("id_pelapak") id_pelapak: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("provinsi_id") provinsi_id: String,
        @Field("city_id") city_id: String,
        @Field("alamat") alamat: String,
        @Field("kode_pos") kode_pos: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("email") email: String
    ): Single<pelapak>

    @GET("feature/rekening/{id_pelapak}")
    fun rekening_tampil(@Path("id_pelapak") id_pelapak: String): Single<rekening>

    @GET("feature/penarikan/{id_pelapak}")
    fun penarikan_tampil(@Path("id_pelapak") id_pelapak: String): Single<penarikan>

    @FormUrlEncoded
    @POST("feature/penarikan")
    fun penarikan_simpan(
        @Field("pelapak_id") pelapak_id: String,
        @Field("rekening_pelapak_id") rekening_pelapak_id: String,
        @Field("nominal") nominal: String
    ): Single<penarikan>

    @FormUrlEncoded
    @PUT("feature/penarikan/{id_withdraw}")
    fun penarikan_edit(
        @Path("id_withdraw") id_pelapak: String,
        @Field("pelapak_id") pelapak_id: String,
        @Field("rekening_pelapak_id") rekening_pelapak_id: String,
        @Field("nominal") nominal: String
    ): Single<penarikan>

    @DELETE("feature/penarikan/{id_withdraw}")
    fun penarikan_hapus (@Path("id_withdraw") id_withdraw: String): Single<penarikan>
}
