package deu.cse.tos.retrofit

import androidx.annotation.CheckResult
import deu.cse.tos.Data.BrushListDTO
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface RetrofitToothInterface {
    @FormUrlEncoded
    @POST("tooth/insert_brushlist")
    fun postBrushListResult(@FieldMap param: HashMap<String?, Any?>?): Call<CheckResult?>?

    @FormUrlEncoded
    @POST("tooth/select_brushlist")
    fun postBrushListSelectResult(@FieldMap param: HashMap<String?, Any?>?): Call<BrushListDTO?>?
}