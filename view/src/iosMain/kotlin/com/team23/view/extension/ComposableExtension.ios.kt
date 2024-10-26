package com.team23.view.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getCurrentScreenWidth(): Dp =
    with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }

@Composable
actual fun BackHandler(enabled: Boolean, onBackPressed: () -> Unit) {
}