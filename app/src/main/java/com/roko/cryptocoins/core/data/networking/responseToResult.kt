package com.roko.cryptocoins.core.data.networking

import com.roko.cryptocoins.core.domain.util.NetworkError
import com.roko.cryptocoins.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
) : Result<T, NetworkError> = when(response.status.value) {
    in 200..299 -> {
        try {
            Result.Success(response.body<T>())
        } catch (e: NoTransformationFoundException) {
            Result.Failure(NetworkError.SERIALIZATION_ERROR)
        }
    }
    408 -> Result.Failure(NetworkError.REQUEST_TIMEOUT)
    429 -> Result.Failure(NetworkError.TOO_MANY_REQUESTS)
    in 500..599 -> {
        Result.Failure(NetworkError.SERVER_ERROR)
    }
    else -> Result.Failure(NetworkError.UNKNOWN_ERROR)
}