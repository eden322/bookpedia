package org.pandatech.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import org.pandatech.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App() }