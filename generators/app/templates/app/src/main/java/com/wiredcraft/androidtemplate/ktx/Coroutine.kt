package com.wiredcraft.androidtemplate.ktx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Creates the main [CoroutineScope] for Repository components.
 * The resulting scope has [SupervisorJob] and [Dispatchers.Main] context elements.
 * If you want to append additional elements to the repo scope, use [CoroutineScope.plus] operator:
 * `val scope = RepoScope() + CoroutineName("MyActivity")`.
 */
@Suppress("FunctionName")
fun RepoScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)