package <%= appPackage %>.ktx

fun Double?.orZero(): Double = this ?: .0

fun Float?.orZero(): Float = this ?: 0f

fun Long?.orZero(): Long = this ?: 0L

fun Int?.orZero(): Int = this ?: 0