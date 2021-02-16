package com.githubapp.data

import com.githubapp.data.NetworkErrors.ERROR_CHECK_NETWORK_CONNECTION
import com.githubapp.data.NetworkErrors.ERROR_UNKNOWN
import com.githubapp.data.NetworkErrors.NETWORK_ERROR
import com.githubapp.data.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.githubapp.data.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.githubapp.data.NetworkErrors.isNetworkError
import com.githubapp.util.Constants.NETWORK_TIMEOUT
import com.githubapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> apiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> = withContext(dispatcher) {
    try {
        // throws TimeoutCancellationException
        withTimeout(NETWORK_TIMEOUT) {
            Resource.Success(apiCall.invoke())
        }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is TimeoutCancellationException -> {
                Resource.Error(NETWORK_ERROR_TIMEOUT)
            }
            is IOException -> {
                throwable.localizedMessage?.let { message ->
                    if (isNetworkError(message)) {
                        Timber.e(ERROR_CHECK_NETWORK_CONNECTION)
                        return@withContext Resource.Error(ERROR_CHECK_NETWORK_CONNECTION)
                    }
                }
                Resource.Error(NETWORK_ERROR)
            }
            is HttpException -> {
                val errorResponse = convertErrorBody(throwable)
                Timber.e(errorResponse)
                Resource.Error(errorResponse ?: ERROR_UNKNOWN)
            }
            else -> {
                Resource.Error(NETWORK_ERROR_UNKNOWN)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.localizedMessage
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}
