package rs.peles.presentation.util

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import rs.peles.presentation.util.Consts.EMAIL_ADDRESS_PATTERN
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Double.formatNumber(currency: String): String {
    val pattern = "###,###.### $currency"
    val decimalFormat = DecimalFormat(pattern)
    return decimalFormat.format(this)
}

fun String.toDate(format: String): Date {
    val date: Date
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    date = try {
        simpleDateFormat.parse(this) ?: Date()
    } catch (e: ParseException) {
        Date()
    }
    return date
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Fragment.showSnackBar(message: String) {
    this.view?.rootView?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.isValidEmail(): Boolean {
    val pattern: Pattern = EMAIL_ADDRESS_PATTERN
    return pattern.matcher(this).matches()
}

fun String.isValidPassword(minLen: Int, classes: Int): Boolean {
    var classNumber = 0

    if(this.length < minLen) return false

    if(hasCapital(this)) classNumber++
    if(hasLowerCase(this)) classNumber++
    if(hasDigit(this)) classNumber++
    if(hasSpecialCharacter(this)) classNumber++

    return classNumber >= classes
}

fun hasCapital(s: String): Boolean {
    return s.contains("[A-Z]".toRegex())
}

fun hasLowerCase(s: String): Boolean {
    return s.contains("[a-z]".toRegex())
}

fun hasDigit(s: String): Boolean {
    return s.contains("[0-9]".toRegex())
}

fun hasSpecialCharacter(s: String): Boolean {
    return s.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())
}

// Extensions for communicating between fragments
fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}