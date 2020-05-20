package com.wiredcraft.androidtemplate.util

object PasswordUtil {
    val LENGTH_RANGE = 8..32
    val LETTER_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z]).*")
    val LOWER_LETTER_REGEX = Regex("^(?=.*[a-z]).*")
    val UPPER_LETTER_REGEX = Regex("^(?=.*[A-Z]).*")
    val NUM_REGEX = Regex(".*\\d.*")
    val SPECIAL_CHAR_REGEX = Regex(".*[\\[\\]{}\\\\|;:'\",<.>/?~!@#$%^&*()_+=\\-].*")

    fun validLength(password: String): Boolean {
        return password.length in LENGTH_RANGE
    }

    fun matchLetter(password: String): Boolean {
        return LETTER_REGEX.matches(password)
    }

    fun matchLowerLetterSpec(password: String): Boolean =
        LOWER_LETTER_REGEX.matches(password)

    fun matchUpperLetterSpec(password: String): Boolean =
        UPPER_LETTER_REGEX.matches(password)

    fun matchNumOrSpecialChar(password: String): Boolean {
        val hasNumber = NUM_REGEX.matches(password)

        val hasSpecialChar = SPECIAL_CHAR_REGEX.matches(password)

        return hasNumber || hasSpecialChar
    }

    fun isValid(password: String): Boolean {
        return validLength(password) && matchLetter(password)
            && matchLowerLetterSpec(password)
            && matchUpperLetterSpec(password)
            && matchNumOrSpecialChar(password)
    }
}
