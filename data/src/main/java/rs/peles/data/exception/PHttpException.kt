package rs.peles.data.exception

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class PHttpException(private val response: Response<Any>): HttpException(response) {

    fun getResponseCode(): Int =
        response.code()

    fun getErrorResponseBodyString(): String {
        if(response.errorBody() != null) {
            return response.errorBody().toString()
        }
        return ""
    }

    fun getErrorResponseBody(): ResponseBody? {
        return response.errorBody()
    }

}