package com.andreperkins.maddenassistant

import com.andreperkins.maddenassistant.models.PlayType
import io.reactivex.Observable

/** Any class that implements this interface is responsible for rendering [MainViewState]. */
interface MainView {

    /** Produces updates whenever the user has selected a [PlayType] */
    fun userSelectedPlayType(): Observable<PlayType>

    /** Called to indicate that there is a new [MainViewState] to display. */
    fun render(state: MainViewState)
}