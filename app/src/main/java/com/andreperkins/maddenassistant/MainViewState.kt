package com.andreperkins.maddenassistant

import com.andreperkins.maddenassistant.models.PlayType

/** Model class to represent the state of the [MainView] at any particular time. */
data class MainViewState(val selected: List<PlayType>, val advice: PlayType? = null)