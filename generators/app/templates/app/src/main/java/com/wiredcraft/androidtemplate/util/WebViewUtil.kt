package <%= appPackage %>.util

import android.content.Context
import android.webkit.WebView
import androidx.webkit.WebViewCompat
import <%= appPackage %>.env.WebViewEnv

object WebViewUtil {
    /*
    This bug occurs on webview version below 73
    See: https://bugs.chromium.org/p/chromium/issues/detail?id=925887
     */
    @JvmStatic
    fun isSafeToOpen(context: Context): Boolean {
        val info = WebViewCompat.getCurrentWebViewPackage(context)
        return when (info?.packageName) {
            "com.android.chrome",
            "com.google.android.webview" ->
                findVersions(info.versionName).first >= WebViewEnv.MINIMAL_SAFE_WEBKIT_VERSION
            "com.huawei.webview" -> {
                val (_, versionName) = findEngineVersionName(getUserAgent(context))
                findVersions(versionName).first >= WebViewEnv.MINIMAL_SAFE_WEBKIT_VERSION
            }
            else -> false
        }
    }

    private fun getUserAgent(context: Context): String {
        return WebView(context).settings.userAgentString
    }

    private fun findEngineVersionName(userAgent: String): Pair<String, String> {
        val regex = """(Chrom[\w]+)/([\d.]+)""".toRegex()
        val matchResult = regex.find(userAgent)
        val (_, engine, versionName) = matchResult?.groupValues.orEmpty()

        return Pair(engine, versionName)
    }

    private fun findVersions(versionName: String): Triple<Int, Int, Int> {
        val regex = """([\d]+).([\d]+).([\d]+)""".toRegex()
        val matchResult = regex.find(versionName)
        val (_, major, medium, minor) = matchResult?.groupValues.orEmpty()

        return Triple(major.toInt(), medium.toInt(), minor.toInt())
    }
}