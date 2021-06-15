package deu.cse.tos.retrofit

import androidx.annotation.CheckResult
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface RetrofitCheckAccountInterface {
    @FormUrlEncoded
    @POST("auth/check")
    fun postCheckUserResult(@FieldMap param: HashMap<String?, Any?>?): Call<CheckResult?>?

    @FormUrlEncoded
    @POST("register/user")
    fun postInsertUserResult(@FieldMap param: HashMap<String?, Any?>?): Call<CheckResult?>?

}