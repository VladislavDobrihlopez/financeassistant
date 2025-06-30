package com.dobrihlopez.financeassistant.core_ui.animation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.ui.Alignment

val actionEnterTransition =
    fadeIn() +
        expandHorizontally(
            clip = false,
            expandFrom = Alignment.Start,
        )

val navActionEnterTransition =
    fadeIn() +
        expandHorizontally(
            clip = false,
            expandFrom = Alignment.End,
        )

val navActionExitTransition =
    fadeOut() +
        shrinkHorizontally(
            shrinkTowards = Alignment.Start,
            clip = false,
        )
