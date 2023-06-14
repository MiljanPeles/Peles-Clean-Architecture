package rs.peles.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object Consts {

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z][a-zA-Z\\-]{0,25}" +
                ")+"
    )
    const val PASSWORD_CLASSES = 2
    const val PASSWORD_MIN_LEN = 5

    fun getRandomImageFileName(): String = "JPEG_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
        Date()
    ) + "_"
}