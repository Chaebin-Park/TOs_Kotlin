package deu.cse.tos.retrofit

import deu.cse.tos.Data.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("auth/info")
    fun postUserResult(@FieldMap param: HashMap<String?, Any?>?): Call<UserDTO?>?

    @FormUrlEncoded
    @POST("tooth/info")
    fun postToothResult(@FieldMap param: HashMap<String?, Any?>?): Call<ToothInfoDTO?>?

    @FormUrlEncoded
    @POST("tooth/yearscore")
    fun postYearGraphResult(@FieldMap param: HashMap<String?, Any?>?): Call<YearGraphDTO?>?

    @FormUrlEncoded
    @POST("tooth/insight")
    fun postInsightResult(@FieldMap param: HashMap<String?, Any?>?): Call<InsightDTO?>?

    @FormUrlEncoded
    @POST("tooth/todayscore")
    fun postTodayGraphResult(@FieldMap param: HashMap<String?, Any?>?): Call<TodayGraphDTO?>?

    @FormUrlEncoded
    @POST("qna/select_qnalist")
    fun postQnAResult(@FieldMap param: HashMap<String?, Any?>?): Call<QnaDTO?>?
}