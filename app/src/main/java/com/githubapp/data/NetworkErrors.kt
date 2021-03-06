package com.githubapp.data

object NetworkErrors {

    private const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
    const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection"
    const val NETWORK_ERROR_UNKNOWN = "Unknown network error"
    const val NETWORK_ERROR = "Network error"
    const val NETWORK_ERROR_TIMEOUT = "Network timeout"
    const val ERROR_UNKNOWN = "Unknown error"


    fun isNetworkError(msg: String): Boolean{
        return when{
            msg.contains(UNABLE_TO_RESOLVE_HOST) -> true
            else-> false
        }
    }
}