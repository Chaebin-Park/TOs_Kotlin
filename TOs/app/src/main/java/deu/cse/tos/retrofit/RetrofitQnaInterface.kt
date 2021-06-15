package deu.cse.tos.retrofit

import deu.cse.tos.Data.QnaDTO
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface RetrofitQnaInterface {
    @FormUrlEncoded
    @POST("qna/select_qnalist")
    fun postQnAResult(@FieldMap param: HashMap<String?, Any?>?): Call<QnaDTO?>?
}