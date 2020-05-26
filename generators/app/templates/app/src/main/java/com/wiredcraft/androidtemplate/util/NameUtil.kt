package <%= appPackage %>.util

import com.github.promeg.pinyinhelper.Pinyin

object NameUtil {
    private val VALID_NAME_REGEX = Regex("[a-zA-Z]+")

    fun removeInvalidCharFromChinese(chinese: String): String {
        return chinese.filter {
            Pinyin.toPinyin(it).matches(VALID_NAME_REGEX)
        }
    }

    fun removeInvalidChar(name: String): String {
        return name.filter {
            it.toString().matches(VALID_NAME_REGEX)
        }
    }

    fun getPinYinName(name: String): String {
        if (name.isNotEmpty()) {
            return toPinYin(name)
        }
        return ""
    }

    private fun toPinYin(string: String): String {
        return Pinyin.toPinyin(string, "").let { pinYin ->
            if (pinYin.isNotEmpty()) pinYin.substring(0, 1) + pinYin.substring(1).toLowerCase() else ""
        }
    }
}
