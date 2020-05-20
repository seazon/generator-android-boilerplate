package com.wiredcraft.androidtemplate.ktx

import android.os.Bundle

typealias NoArgsCallback = () -> Unit

typealias ToTpCallback = (totp: String?) -> Unit
typealias SuspendToTpCallback = suspend (totp: String?) -> Unit

typealias InlineScreenChangeListener = (arguments: Bundle?) -> Unit

typealias OnGraphQLErrors = suspend (errors: List<Error>) -> Unit
typealias OnCoroutineTry = suspend () -> Unit
typealias preCoroutineTry = suspend () -> Unit
typealias OnCoroutineCatch = suspend (e: Exception) -> Unit
typealias OnCoroutineFinal = suspend () -> Unit
typealias OnHttpSuccess = suspend () -> Unit
typealias OnHttpFailure = suspend (code: Int?) -> Unit
typealias OnTokenizeSuccess = suspend (Pair<String, String>) -> Unit
typealias OnHttpException = suspend (e: Exception) -> Unit
