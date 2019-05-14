package com.delacrixmorgan.kingscup.statemachine

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * FiniteStateMachineRender
 * kingscup-android
 *
 * Created by Delacrix Morgan on 14/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

interface FSMStateRenderer<T : FSMState> {
    fun render(state: T)
}

data class AnyStateRenderer<RenderState : FSMState>(val renderer: FSMStateRenderer<RenderState>) : FSMStateRenderer<RenderState> {

    private val weakRenderer: WeakReference<FSMStateRenderer<RenderState>> = WeakReference(this.renderer)

    override fun render(state: RenderState) {
        this.weakRenderer.get()?.apply {
            this.render(state)
        }
    }
}

interface RenderableFiniteStateMachine<RenderState : FSMState, StateRenderer : AnyStateRenderer<RenderState>> : FiniteStateMachine<RenderState> {
    var renderer: StateRenderer
}

abstract class ObservableStateMachine<State : FSMState>(private val startState: State) : ViewModel(), FiniteStateMachine<State> {
    override var state: State
        get() {
            return liveState.value ?: startState
        }
        set(newValue) {
            liveState.postValue(newValue)
        }

    private val liveState = MutableLiveData<State>()

    init {
        this.liveState.value = startState
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        this.liveState.observe(owner, observer)
    }
}