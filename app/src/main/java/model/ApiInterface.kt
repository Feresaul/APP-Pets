package model

import android.content.Context
import com.example.petsapp.R
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class RetrofitConnection{
    companion object{
        var api: ApiInterface ?= null
    }

    fun getApiInterface(context: Context): ApiInterface? {
        if(api==null) {
            api = createRetrofitConnection(context)
        }
        return api
    }

    private fun createRetrofitConnection(context: Context): ApiInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @POST("usuarios/login")
        fun logIn(@Body usuario: User): Call<ResponseT<Int>>

        @POST("usuarios/registro")
        fun singUp(@Body usuario: User): Call<ResponseT<Int>>

        @POST("servicios/agregarCita")
        fun addService(@Body addServiceItem: AddServiceItem): Call<ResponseT<Int>>

        @POST("servicios/obtenerHoras")
        fun getServiceHours(@Body servicio: ServiceHours): Call<ResponseT<ServiceHoursOut>>

        @GET("servicios/obtenerPorTipo")
        fun getServicesByType(@Query("tipo") tipo: String): Call<ResponseT<ArrayList<ServiceItem>>>

        @GET("servicios/historial")
        fun getHistory(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

        @GET("servicios/citasPendientes")
        fun getServices(@Query("idUsuario") idUsuario: Int): Call<ResponseT<ArrayList<ServiceItem>>>

        @DELETE("servicios/cancelarCita")
        fun deleteService(@Query("idCita") idCita: Int): Call<ResponseT<Int>>
    }
}

