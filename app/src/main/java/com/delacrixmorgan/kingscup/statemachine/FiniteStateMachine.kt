package com.delacrixmorgan.kingscup.statemachine

interface FSMState

interface FSMTransitions

interface FiniteStateMachine<T : FSMState> : FSMTransitions {
    val state: T
}
