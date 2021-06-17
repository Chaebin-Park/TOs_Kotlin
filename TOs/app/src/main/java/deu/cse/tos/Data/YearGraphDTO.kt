package deu.cse.tos.data

import com.google.gson.annotations.SerializedName

data class YearGraphDTO (
    @SerializedName("01") val jan: Float,
    @SerializedName("02") val feb: Float,
    @SerializedName("03") val mar: Float,
    @SerializedName("04") val apr: Float,
    @SerializedName("05") val may: Float,
    @SerializedName("06") val jun: Float,
    @SerializedName("07") val jul: Float,
    @SerializedName("08") val aug: Float,
    @SerializedName("09") val sep: Float,
    @SerializedName("10") val oct: Float,
    @SerializedName("11") val nov: Float,
    @SerializedName("12") val dec: Float,
)