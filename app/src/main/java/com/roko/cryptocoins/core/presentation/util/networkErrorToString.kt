package com.roko.cryptocoins.core.presentation.util

import android.content.Context
import com.roko.cryptocoins.R
import com.roko.cryptocoins.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String = when(this) {
    NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
    NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
    NetworkError.NO_INTERNET_CONNECTION -> R.string.error_no_internet
    NetworkError.SERVER_ERROR -> R.string.error_server_error
    NetworkError.SERIALIZATION_ERROR -> R.string.error_serialization_error
    NetworkError.UNKNOWN_ERROR -> R.string.error_unknown
}.let { stringId ->
    context.getString(stringId)
}