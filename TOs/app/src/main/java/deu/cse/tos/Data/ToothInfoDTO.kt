package deu.cse.tos.data

import com.google.gson.annotations.SerializedName

data class ToothInfoDTO (
    @SerializedName("morning_time") val morningTime: String,
    @SerializedName("afternoon_time")   val afternoonTime: String,
    @SerializedName("dinner_time")  val dinnerTime: String,
    @SerializedName("night_time")   val nightTime: String,

    @SerializedName("morning_count")    val morningCount: Int,
    @SerializedName("afternoon_count")  val afternoonCount: Int,
    @SerializedName("dinner_count") val dinnerCount: Int,
    @SerializedName("night_count")  val nightCount: Int

)