package rs.peles.cleanarchitecture.util

import rs.peles.cleanarchitecture.R

class ErrorMessageFactory private constructor() {

    companion object {

        fun create(e: Exception): Int {
            var messageResId = R.string.string_basic_error

            return messageResId
        }

    }

}