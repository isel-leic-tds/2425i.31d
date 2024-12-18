package view

enum class SlotImage(val filename: String) {
    APPLE("apple.png"),
    BAR("bar.png"),
    BELL("bell.png"),
    CHERRIES("cherries.png"),
    DIAMOND("diamond.png"),
    GRAPES("grapes.png"),
    LEMON("lemon.png"),
    SEVEN("seven.png"),
    WATERMELON("watermelon.png"),
    BANANA("banana.png");
}

fun Byte.toSlotImageFilename(): String {
    require(this in SlotImage.entries.indices) { "Invalid slot value: $this" }
    return SlotImage.entries[this.toInt()].filename
}