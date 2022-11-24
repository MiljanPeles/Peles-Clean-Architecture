package rs.peles.presentation.util

import rs.peles.presentation.R

class ErrorMessageFactory private constructor() {

    companion object {

        fun create(e: Exception): Int {
            var messageResId = R.string.string_basic_error

            return messageResId
        }

    }

}