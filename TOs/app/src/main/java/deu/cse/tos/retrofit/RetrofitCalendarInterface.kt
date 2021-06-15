package deu.cse.tos.retrofit

import deu.cse.tos.Data.ToothInfoDTO
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import kotlin.collections.HashMap

interface RetrofitCalendarInterface {
    @FormUrlEncoded
    @POST("calendar/info")
    fun postCalendarResult(@FieldMap param: HashMap<String, Any>): Call<ToothInfoDTO?>?
}