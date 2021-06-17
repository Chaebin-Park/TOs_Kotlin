package deu.cse.tos.retrofit

import deu.cse.tos.data.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface API {
    @FormUrlEncoded
    @POST("qna/select_qnalist")
    fun postQnaResult(@FieldMap param: HashMap<String, Any>): Call<QnaDTO>

    @FormUrlEncoded
    @POST("tooth/insight")
    fun postInsightResult(@FieldMap param: HashMap<String, Any>): Call<InsightDTO>

    @FormUrlEncoded
    @POST("tooth/yearscore")
    fun postYearGraphResult(@FieldMap param: HashMap<String, Any>): Call<YearGraphDTO>

    @FormUrlEncoded
    @POST("calendar/info")
    fun postCalendarResult(@FieldMap param: HashMap<String, Any>): Call<ToothInfoDTO>

    @FormUrlEncoded
    @POST("tooth/insert_brushlist")
    fun postBrushListResult(@FieldMap param: HashMap<String, Any>): Call<CheckResultDTO>
    @FormUrlEncoded
    @POST("tooth/select_brushlist")
    fun postBrushListSelectResult(@FieldMap param: HashMap<String, Any>): Call<BrushListDTO>
}