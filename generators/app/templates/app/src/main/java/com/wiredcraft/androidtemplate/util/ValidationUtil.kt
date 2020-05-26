package <%= appPackage %>.util

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.util.regex.Pattern
import javax.inject.Inject

@Deprecated("Using Validator instead")
object ValidationUtil {
    private const val CORPORATE_CODE_MINIMAL_LEN = 7
    private const val USERNAME_MINIMAL_LEN = 4

    @JvmStatic
    private val PASSWORD_LEN_RANGE = 8..32

    @JvmStatic
    private val PASSWORD_REGEX_PATTERN =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9\\[\\]{}|;:’“,<.>/?~!@#\$%^&*()_+=-]).+$")

    @JvmStatic
    private val EMAIL_REGEX_PATTERN =
        Regex("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")

    @JvmStatic
    private val CORPORATE_CODE_VALIDATION_PATTERN =
        Regex("^[A-Z,0-9]{1,22}$")

    @JvmStatic
    private val PROMOTION_CODE_VALIDATION_PATTERN =
        Regex("^[A-Z,0-9]{1,6}$")

    @JvmStatic
    private val GROUP_CODE_VALIDATION_PATTERN =
        Regex("^[A-Z,0-9]{2,6}$")

    /*
    * Rule 1
    Given I have entered a corporate code with non-digits
    Then the app must be take all non-digits out
    AND the app must add 0 to start of the string till it is 7 digits long

    * Rule 2
    Given I have entered a digits only corporate code that is less than 7 digits
    Then the app must add 0 to start of the string till it is 7 digits long
     */
    fun processCorporateCode(raw: String?): String? {
        if (raw !is String || raw.isBlank()) return raw

        val digitsOnly = raw.filter { it.isDigit() }
        return if (digitsOnly.length < CORPORATE_CODE_MINIMAL_LEN) {
            val diff = CORPORATE_CODE_MINIMAL_LEN - digitsOnly.length

            buildString {
                for (i in 0 until diff) {
                    append("0")
                }
                append(digitsOnly)
            }
        } else {
            digitsOnly
        }
    }

    @JvmStatic
    fun isCorporateCodeValid(code: String?): Boolean {
        return code is String && CORPORATE_CODE_VALIDATION_PATTERN.matches(code)
    }

    @JvmStatic
    fun isPromotionCodeValid(code: String?): Boolean {
        return code is String && PROMOTION_CODE_VALIDATION_PATTERN.matches(code)
    }

    @JvmStatic
    fun isGroupCodeValid(code: String?): Boolean {
        return code is String && GROUP_CODE_VALIDATION_PATTERN.matches(code)
    }

    @JvmStatic
    fun isUsernameLenValid(username: String?): Boolean {
        return username is String && username.length >= USERNAME_MINIMAL_LEN
    }

    @JvmStatic
    fun isPasswordLenValid(password: String?): Boolean {
        return password is String &&
            password.length in PASSWORD_LEN_RANGE
    }

    @JvmStatic
    fun isPasswordContentValid(password: String?): Boolean {
        return password is String &&
            PASSWORD_REGEX_PATTERN.matches(password)
    }

    @JvmStatic
    fun isChineseMobilellNO(mobiles: String?): Boolean {
        return if (mobiles == null) {
            false
        } else {
            val p = Pattern.compile("^\\d{11}$")
            val m = p.matcher(mobiles)
            m.matches()
        }
    }

    @JvmStatic
    fun isValidChineseMobileNO(mobiles: String?): Boolean {
        return if (mobiles == null) {
            false
        } else {
            val p = Pattern.compile("^((1[3456789][0-9]))[0-9]{8}$")
            val m = p.matcher(mobiles)
            m.matches()
        }
    }

    @JvmStatic
    fun containsChinese(str: String): Boolean {
        val p = Pattern.compile("[\u4e00-\u9fa5]+")
        return p.matcher(str).matches()
    }

    fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX_PATTERN.matches(email)
    }

    fun isValidPhone(util: PhoneNumberUtil, countryCode: String, number: String): Boolean {
        return try {
            val result = util.parse("+$countryCode$number", null)
            val isMobile = util.isPossibleNumberForType(
                result,
                PhoneNumberUtil.PhoneNumberType.MOBILE
            )
            if (isMobile && countryCode == "86") {
                return isValidChineseMobileNO(number)
            }
            return isMobile
        } catch (e: Exception) {
            false
        }
    }
}

class Validator @Inject constructor(
    private val mPhoneNumberUtil: PhoneNumberUtil
) {
    companion object {
        private const val CORPORATE_CODE_MINIMAL_LEN = 7
        private const val USERNAME_MINIMAL_LEN = 4

        @JvmStatic
        private val PASSWORD_LEN_RANGE = 8..32

        @JvmStatic
        private val PASSWORD_REGEX_PATTERN =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9\\[\\]{}|;:’“,<.>/?~!@#\$%^&*()_+=-]).+$")

        @JvmStatic
        private val EMAIL_REGEX_PATTERN =
            Regex("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")

        @JvmStatic
        private val CORPORATE_CODE_VALIDATION_PATTERN =
            Regex("^[A-Z,0-9]{1,22}$")

        @JvmStatic
        private val PROMOTION_CODE_VALIDATION_PATTERN =
            Regex("^[A-Z,0-9]{1,6}$")

        @JvmStatic
        private val GROUP_CODE_VALIDATION_PATTERN =
            Regex("^[A-Z,0-9]{2,6}$")
    }

    /*
    * Rule 1
    Given I have entered a corporate code with non-digits
    Then the app must be take all non-digits out
    AND the app must add 0 to start of the string till it is 7 digits long

    * Rule 2
    Given I have entered a digits only corporate code that is less than 7 digits
    Then the app must add 0 to start of the string till it is 7 digits long
     */
    fun processCorporateCode(raw: String?): String? {
        if (raw !is String || raw.isBlank()) return raw

        val digitsOnly = raw.filter { it.isDigit() }
        return if (digitsOnly.length < CORPORATE_CODE_MINIMAL_LEN) {
            val diff = CORPORATE_CODE_MINIMAL_LEN - digitsOnly.length

            buildString {
                for (i in 0 until diff) {
                    append("0")
                }
                append(digitsOnly)
            }
        } else {
            digitsOnly
        }
    }

    fun isCorporateCodeValid(code: String?): Boolean {
        return code is String && CORPORATE_CODE_VALIDATION_PATTERN.matches(code)
    }

    fun isPromotionCodeValid(code: String?): Boolean {
        return code is String && PROMOTION_CODE_VALIDATION_PATTERN.matches(code)
    }

    fun isGroupCodeValid(code: String?): Boolean {
        return code is String && GROUP_CODE_VALIDATION_PATTERN.matches(code)
    }

    fun isUsernameLenValid(username: String?): Boolean {
        return username is String && username.length >= USERNAME_MINIMAL_LEN
    }

    fun isPasswordLenValid(password: String?): Boolean {
        return password is String &&
            password.length in PASSWORD_LEN_RANGE
    }

    fun isPasswordContentValid(password: String?): Boolean {
        return password is String &&
            PASSWORD_REGEX_PATTERN.matches(password)
    }

    fun isChineseMobilellNO(mobiles: String?): Boolean {
        return if (mobiles == null) {
            false
        } else {
            val p = Pattern.compile("^\\d{11}$")
            val m = p.matcher(mobiles)
            m.matches()
        }
    }

    fun isValidChineseMobileNO(mobiles: String?): Boolean {
        return if (mobiles == null) {
            false
        } else {
            val p = Pattern.compile("^((1[3456789][0-9]))[0-9]{8}$")
            val m = p.matcher(mobiles)
            m.matches()
        }
    }

    fun containsChinese(str: String): Boolean {
        val p = Pattern.compile("[\u4e00-\u9fa5]+")
        return p.matcher(str).matches()
    }

    fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX_PATTERN.matches(email)
    }

    fun isValidPhone(countryCode: String, number: String): Boolean {
        return try {
            val result = mPhoneNumberUtil.parse("+$countryCode$number", null)
            val isMobile = mPhoneNumberUtil.isPossibleNumberForType(
                result,
                PhoneNumberUtil.PhoneNumberType.MOBILE
            )
            if (isMobile && countryCode == "86") {
                return isValidChineseMobileNO(number)
            }
            return isMobile
        } catch (e: Exception) {
            false
        }
    }
}