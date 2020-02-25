package com.delacrixmorgan.kingscup.statemachine

/**
 * FiniteStateMachineList
 * kingscup-android
 *
 * Created by Delacrix Morgan on 19/02/2020.
 * Copyright (c) 2020 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

interface FSMListState : FSMState

sealed class ListState<Type> : FSMListState {
    class Start<T> : ListState<T>()
    class Loading<T> : ListState<T>()
    data class Presenting<T>(val items: Array<T>) : ListState<T>()
    data class ShowingError(val error: Exception)
    data class ShowingDetail<T>(val item: T, val items: Array<T>) : ListState<T>()
    data class Completed<T>(val result: T?) : ListState<T>()
}

interface ListTransitions<Type>: FSMTransitions {
    fun refresh()
    fun success(items: Array<Type>)
    fun fail(error: Exception)
    fun retry()
    fun back(items: Array<Type>)
    fun dismiss()
}

interface DetailsListTransitions<Type>: ListTransitions<Type> {
    fun displayDetails(item: Type)
}

interface SelectableListTransitions<Type>: ListTransitions<Type> {
    fun selectItem(item: Type)
}

interface ListStateMachineProtocol<Type> : ListTransitions<Type>, FiniteStateMachine<ListState<Type>>