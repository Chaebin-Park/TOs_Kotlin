package deu.cse.tos.retrofit

import androidx.annotation.CheckResult
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface RetrofitSelfCheckInterface {
    @FormUrlEncoded
    @POST("tooth/insert_calendar")
    fun postSelfUserResult(@FieldMap param: HashMap<String?, Any?>?): Call<CheckResult?>?

}