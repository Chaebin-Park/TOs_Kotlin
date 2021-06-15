package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class BrushListDTO {
    @SerializedName("block")
    private var data: List<BrushDTO?>? = null

    override fun toString(): String {
        return "BrushListDTO{" +
                "data=" + data +
                '}'
    }

    inner class BrushDTO {
        @SerializedName("hash_key")
        val hashKey: String? = null

        @SerializedName("item_name")
        val itemName: String? = null

        @SerializedName("buy_date")
        val buyDate: String? = null

        @SerializedName("recommend_date")
        val recommendDate: String? = null

        @SerializedName("using_date")
        val usingDate = 0

        @SerializedName("remain_recommend_date")
        val remainRecommendDate = 0

        override fun toString(): String {
            return "BrushList{" +
                    "hash_key='" + hashKey + '\'' +
                    ", item_name='" + itemName + '\'' +
                    ", buy_date='" + buyDate + '\'' +
                    ", recommend_date='" + recommendDate + '\'' +
                    ", using_date='" + usingDate + '\'' +
                    ", remain_recommend_date='" + remainRecommendDate + '\'' +
                    '}'
        }
    }
}