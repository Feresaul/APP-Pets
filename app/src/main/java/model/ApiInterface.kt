package model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("ObtenerUsuarios")
    fun getUsers(): Call<ResponseT<ArrayList<Usuario>>>

    @GET("ObtenerUsuarioPorId")
    fun getUser(@Query("id") id: Int): Call<ResponseT<Usuario>>

    @GET("posts")
    fun getPosts(): Call<ArrayList<Post>>
}