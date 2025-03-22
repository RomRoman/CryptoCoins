package com.roko.cryptocoins.core.data.networking

import com.roko.cryptocoins.core.domain.util.NetworkError
import com.roko.cryptocoins.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute : () -> HttpResponse
) : Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Failure(NetworkError.NO_INTERNET_CONNECTION)
    } catch (e: SerializationException) {
        return Result.Failure(NetworkError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Failure(NetworkError.UNKNOWN_ERROR)
    }

    return responseToResult(response)

}