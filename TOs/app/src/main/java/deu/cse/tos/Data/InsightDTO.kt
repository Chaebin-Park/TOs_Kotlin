package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class InsightDTO {
    @SerializedName("tooth_score")
    private var toothScore = 0

    @SerializedName("month_tooth_number")
    private var monthToothNumber = 0

    override fun toString(): String {
        return "InsightDTO{" +
                "tooth_score=" + toothScore +
                ", month_tooth_number=" + monthToothNumber +
                '}'
    }
}