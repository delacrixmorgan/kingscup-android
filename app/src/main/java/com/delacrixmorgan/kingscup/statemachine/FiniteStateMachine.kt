package com.delacrixmorgan.kingscup.statemachine

/**
 * FiniteStateMachine
 * kingscup-android
 *
 * Created by Delacrix Morgan on 14/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

sealed class FSMError {
    object INVALID_STATE_TRANSITION : Error()
}

interface FSMState

interface FSMTransitions

interface FiniteStateMachine<T : FSMState> : FSMTransitions {
    val state: T
}
