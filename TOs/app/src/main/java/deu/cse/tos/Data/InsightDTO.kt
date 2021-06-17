package deu.cse.tos.data

import com.google.gson.annotations.SerializedName

data class InsightDTO (
    @SerializedName("tooth_score")  val score: Int,
    @SerializedName("month_tooth_number")   val number: Int
)