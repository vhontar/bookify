package com.vhontar.bookify.aaa.network.response

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
class Response<T> {
    var status: ResponseStatus = ResponseStatus.NONE
    var errorMessage: String = ""
    var errorCode: Int = 0
    var data: T? = null

    fun setSuccess() {
        status = ResponseStatus.SUCCESS
    }

    fun setNetworkOff() {
        status = ResponseStatus.NETWORK_OFF
    }

    fun setResponseError() {
        status = ResponseStatus.RESPONSE_ERROR
    }

    fun setServerError() {
        status = ResponseStatus.SERVER_ERROR
    }

    fun setServerDown() {
        status = ResponseStatus.SERVER_DOWN
    }
}