package rs.peles.presentation.util

import retrofit2.HttpException
import rs.peles.presentation.R

class ErrorMessageFactory private constructor() {

    companion object {

        fun create(e: Exception): Int {
            var messageResId = R.string.string_basic_error
            if(e is HttpException) messageResId = R.string.string_server_error
            return messageResId
        }

    }

}