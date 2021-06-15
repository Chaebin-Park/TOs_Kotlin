package deu.cse.tos.Data

import com.google.gson.annotations.SerializedName

class YearGraphDTO {
    @SerializedName("1")
    private var jan = 0f

    @SerializedName("2")
    private var feb = 0f

    @SerializedName("3")
    private var mar = 0f

    @SerializedName("4")
    private var apr = 0f

    @SerializedName("5")
    private var may = 0f

    @SerializedName("6")
    private var june = 0f

    @SerializedName("7")
    private var july = 0f

    @SerializedName("8")
    private var aug = 0f

    @SerializedName("9")
    private var sep = 0f

    @SerializedName("10")
    private var oct = 0f

    @SerializedName("11")
    private var nov = 0f

    @SerializedName("12")
    private var dec = 0f

    fun getJan(): Float {
        return jan
    }

    override fun toString(): String {
        return "YearGraphDTO{" +
                "jan=" + jan +
                ", feb=" + feb +
                ", mar=" + mar +
                ", apr=" + apr +
                ", may=" + may +
                ", june=" + june +
                ", july=" + july +
                ", aug=" + aug +
                ", sep=" + sep +
                ", oct=" + oct +
                ", nov=" + nov +
                ", dec=" + dec +
                '}'
    }
}