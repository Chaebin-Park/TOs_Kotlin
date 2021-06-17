package deu.cse.tos.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val clientBuilder = OkHttpClient.Builder()
    private val loggingInterceptor = HttpLoggingInterceptor()

    val url: String = "http://113.198.235.232:21000/"
    var api: API

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        api = retrofit.create(API::class.java)
    }
}