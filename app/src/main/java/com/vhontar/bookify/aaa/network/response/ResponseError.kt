package com.vhontar.bookify.aaa.network.response

/**
 * Created by Vladyslav Hontar (vhontar) on 30.01.21.
 */
class ResponseError(
    val customMessage: String
) : Throwable()