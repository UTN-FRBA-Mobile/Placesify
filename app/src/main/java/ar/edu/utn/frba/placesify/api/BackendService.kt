package ar.edu.utn.frba.placesify.api

import android.util.Log
import ar.edu.utn.frba.placesify.model.ApiCategoriesResponse
import ar.edu.utn.frba.placesify.model.ApiListResponse
import ar.edu.utn.frba.placesify.model.ApiPutUserResponse
import ar.edu.utn.frba.placesify.model.ApiUserResponse
import ar.edu.utn.frba.placesify.model.Listas
import ar.edu.utn.frba.placesify.model.Usuarios
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BackendService {
    companion object {
        val instance = Retrofit.Builder()
            .baseUrl("https://crudapi.co.uk/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    "Authorization",
                    "Bearer s6A42K8fhYhBeQ7QZD-yhfj6zVAQpWkYPws_ucD_aGKkbJxc9A"
                ).build()
                Log.d("RETROFIT REQUEST", "${request.toString()}")
                val resp = chain.proceed(request)
                // Deal with the response code
                if (resp.code == 200) {
                    try {
                        val myJson =
                            resp.peekBody(2048).string() // peekBody() will not close the response
                        println(myJson)
                    } catch (e: Exception) {
                        println("Error parse json from intercept..............")
                    }
                } else {
                    println(resp)
                }
                resp
            }.build())
            .build().create(BackendService::class.java)
    }

    @GET("listas")
    suspend fun getListas(): ApiListResponse

    @GET("listas/{id}")
    suspend fun getLista(@Path("id") id: String): Listas

    @POST("lista")
    suspend fun addLista(@Body lista: Listas): Listas

    @GET("categorias")
    suspend fun getCategorias(): ApiCategoriesResponse

    @GET("usuarios")
    suspend fun getUsuarios(): ApiUserResponse

    @PUT("usuarios")
    suspend fun putUsuario(@Body usuario: Usuarios):ApiPutUserResponse

    @PUT("usuarios/{id}")
    suspend fun putUsuario2(
        @Path("id") id:String,
        @Body favoritesLists: List<String>):Usuarios

}