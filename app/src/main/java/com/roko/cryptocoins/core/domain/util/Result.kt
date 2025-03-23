package com.roko.cryptocoins.core.domain.util

typealias DomainError = Error
typealias EmptyDataResult<E> = Result<Unit, E>

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Failure<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> = when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(transform(data))
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult() : EmptyDataResult<E> = map {  }

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> = when (this) {
    is Result.Failure -> this
    is Result.Success -> {
        action(data)
        this
    }
}

inline fun <T, E: Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> = when (this) {
    is Result.Success -> this
    is Result.Failure -> {
        action(error)
        this
    }
}


