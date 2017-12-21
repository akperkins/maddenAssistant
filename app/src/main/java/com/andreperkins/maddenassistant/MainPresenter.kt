package com.andreperkins.maddenassistant

/** This class is responsible for creating a bridge between the [MainView] and the
 * [UserSelectionInteractor] with a unidirectional data flow. */
class MainPresenter {

    /** Called to bridge actions between the [view] and the [interactor]. */
    fun bind(view: MainView, interactor: UserSelectionInteractor) {
        interactor.getCurrentState()
                .subscribe { view.render(it) }

        view.userSelectedPlayType()
                .subscribe { interactor.update(it) }
    }
}