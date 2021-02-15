package com.githubapp.data

import com.githubapp.data.NetworkErrors.ERROR_CHECK_NETWORK_CONNECTION
import com.githubapp.data.NetworkErrors.ERROR_UNKNOWN
import com.githubapp.data.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.githubapp.data.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.githubapp.util.Constants.NETWORK_TIMEOUT
import com.githubapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException

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
                Timber.e(NETWORK_ERROR_TIMEOUT)
                Resource.Error(NETWORK_ERROR_TIMEOUT)
            }
            is UnknownHostException -> {
                Timber.e(ERROR_CHECK_NETWORK_CONNECTION)
                Resource.Error(ERROR_CHECK_NETWORK_CONNECTION)
            }
            is IOException -> {
                Timber.e(NETWORK_ERROR_TIMEOUT)
                Resource.Error(NETWORK_ERROR_TIMEOUT)
            }
            is HttpException -> {
                val errorResponse = convertErrorBody(throwable)
                Timber.e(errorResponse)
                Resource.Error(
                    errorResponse!!
                )
            }
            else -> {
                Timber.e(NETWORK_ERROR_UNKNOWN)
                Resource.Error(NETWORK_ERROR_UNKNOWN)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        Timber.e(throwable.localizedMessage)
        throwable.localizedMessage
    } catch (exception: Exception) {
        Timber.e(ERROR_UNKNOWN)
        ERROR_UNKNOWN
    }
}
