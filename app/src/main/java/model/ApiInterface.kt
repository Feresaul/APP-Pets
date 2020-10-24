package model
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("ObtenerUsuarios")
    fun getUsers(): Call<ResponseT<ArrayList<Usuario>>>

    @GET("servicios/obtenerPorTipo")
    fun getServicesByType(@Query("tipo") tipo: String): Call<ResponseT<ArrayList<ServiceItem>>>

    @GET("servicios/historial")
    fun getHistorial(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

    @GET("servicios/citasPendientes")
    fun getServices(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

    @DELETE("servicios/cancelarCita")
    fun deleteService(@Query("idCita") idCita: Int): Call<ResponseT<Int>>
}