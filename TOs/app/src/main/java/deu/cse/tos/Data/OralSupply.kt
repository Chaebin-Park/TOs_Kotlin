package deu.cse.tos.Data

class OralSupply(_remainingDate: Int, _itemName: String?, _recommendedDate: String?) {
    private var remainingDate = 0
    private var itemName: String? = null
    private var recommendedDate: String? = null

    init {
        this.remainingDate = _remainingDate
        this.itemName = _itemName
        this.recommendedDate = _recommendedDate
    }
}