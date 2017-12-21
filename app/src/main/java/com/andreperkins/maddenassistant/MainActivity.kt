package com.andreperkins.maddenassistant

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.andreperkins.maddenassistant.models.PlayType
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject

/** This is the initial [AppCompatActivity] displayed in the application upon app launch.
 * This class attempts to aid a user playing madden by showing which offensive plays should be
 * selected based off of what their opponent has been selecting. */
class MainActivity : AppCompatActivity(), MainView {

    /** Used to publish view updates. */
    private val publishSubject = PublishSubject.create<PlayType>()!!

    /** Used to bind this [MainView] with the business logic from the
     *  [UserSelectionInteractor]. */
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initPresenter()
    }

    private fun initPresenter() {
        presenter = MainPresenter()
        presenter.bind(this, UserSelectionInteractor())
    }

    private fun initUi() {
        getRecyclerView().apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getRecyclerView() = findViewById<RecyclerView>(R.id.options)

    override fun render(state: MainViewState) {
        getRecyclerView().adapter = PlayTypesAdapter(listOf(PlayType("Cover 3"), PlayType("Cover 4"),
                PlayType("Zone Blitz"), PlayType("Man Blitz")))
    }

    override fun userSelectedPlayType() = publishSubject

    /** Used to display the various [PlayType]s that are selectable to the user. */
    inner class PlayTypesAdapter(private var playTypesList: List<PlayType>) :
            RecyclerView.Adapter<PlayTypesAdapter.PlayTypesViewHolder>() {

        override fun onBindViewHolder(holder: PlayTypesViewHolder?, position: Int) = holder!!.update()

        override fun getItemCount() = playTypesList.count()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
                = PlayTypesViewHolder(LayoutInflater.from(parent!!.context).inflate(
                R.layout.play_type_item_row, parent, false))

        inner class PlayTypesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            fun update() {
                val playType = playTypesList[adapterPosition]
                view.findViewById<TextView>(R.id.playOption).apply {
                    text = playType.name
                }.clicks().subscribe {
                    publishSubject.onNext(playType)
                }
            }
        }
    }
}