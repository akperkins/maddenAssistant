package com.andreperkins.maddenassistant

import com.andreperkins.maddenassistant.models.PlayType
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/** Responsible for determining the most likely [PlayType] to run based on opponent's previous
 * decisions.
 *
 * This is the business logic portion of the MVI pattern.
 * */
class UserSelectionInteractor {
    /** Used to maintain the current [PlayType] state. */
    private var currentState = MainViewState(selected = emptyList())

    /** Used to publish results of [MainViewState]. */
    private val subject = BehaviorSubject.create<MainViewState>()!!

    /** Indicates that a new [PlayType] was selected and computes what the new [MainViewState]
     * should be publishes it. */
    fun update(selection: PlayType) {
        currentState = computeState(selection)
        subject.onNext(currentState)
    }

    /** Returns an [Observable] that will produce the latest game state. */
    fun getCurrentState(): Observable<MainViewState> {
        return subject.startWith(currentState)
    }

    private fun computeState(selection: PlayType): MainViewState {
        return currentState
    }
}