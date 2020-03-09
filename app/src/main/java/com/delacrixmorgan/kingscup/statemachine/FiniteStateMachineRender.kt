package com.delacrixmorgan.kingscup.statemachine

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class ObservableStateMachine<State : FSMState>(private val startState: State) :
    ViewModel(), FiniteStateMachine<State> {
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