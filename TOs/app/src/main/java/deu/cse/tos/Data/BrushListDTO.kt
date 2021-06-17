package deu.cse.tos.data

import com.google.gson.annotations.SerializedName

class BrushListDTO {
    @SerializedName("block") var data: List<BrushDTO>? = null

    data class BrushDTO(
        @SerializedName("hash_key") val hashKey: String,
        @SerializedName("item_name") val itemName: String,
        @SerializedName("buy_date") val buyDate: String,
        @SerializedName("recommend_date") val reccDate: String,
        @SerializedName("using_date") val usingDate: Int,
        @SerializedName("remain_recommend_date") val remainReccDate: Int
    )
}