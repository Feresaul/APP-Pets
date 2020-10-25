package model
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("usuarios/login")
    fun logIn(@Body usuario: User): Call<ResponseT<Int>>

    @POST("usuarios/registro")
    fun singUp(@Body usuario: User): Call<ResponseT<Int>>

    @GET("servicios/obtenerPorTipo")
    fun getServicesByType(@Query("tipo") tipo: String): Call<ResponseT<ArrayList<ServiceItem>>>

    @GET("servicios/historial")
    fun getHistorial(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

    @GET("servicios/citasPendientes")
    fun getServices(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

    @DELETE("servicios/cancelarCita")
    fun deleteService(@Query("idCita") idCita: Int): Call<ResponseT<Int>>
}