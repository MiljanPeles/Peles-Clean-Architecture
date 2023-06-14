package rs.peles.presentation.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtensionsTest {

    @Test
    fun emailValidation_isCorrect() {
        val email = "miljan@peles.rs"
        assertThat(email.isValidEmail()).isTrue()
    }

    @Test
    fun passwordValidation_isValid() {
        val password = "miljanPP2022!!"
        val minChars = 8
        val classes = 3
        assertThat(password.isValidPassword(minChars, classes)).isTrue()
    }

    @Test
    fun passwordEmpty_isNotValid() {
        val password = ""
        val minChars = 8
        val classes = 3
        assertThat(password.isValidPassword(minChars, classes)).isFalse()
    }

    @Test
    fun passwordNotEnoughCharacters_isNotValid() {
        val password = "m1ljan!"
        val minChars = 8
        val classes = 3
        assertThat(password.isValidPassword(minChars, classes)).isFalse()
    }

    @Test
    fun passwordNotEnoughClasses_isNotValid() {
        val password = "miljanpeles!!!"
        val minChars = 8
        val classes = 3
        assertThat(password.isValidPassword(minChars, classes)).isFalse()
    }

    @Test
    fun capitalizeFirstCharacterInString_isCapitalized() {
        val stringToCapitalize = "string to capitalize"
        val resultString = stringToCapitalize.capitalize()
        assertThat(resultString[0].isLowerCase()).isFalse()
    }

    @Test
    fun hasCapital_isTrue() {
        val string = "some String"
        // Method check
        val has = hasCapital(string)
        // Manual check
        val manualCheck = string.contains("[A-Z]".toRegex())
        // Must be both true
        assertThat(manualCheck && has).isTrue()
    }

    @Test
    fun hasLowerCase_isTrue() {
        val string = "some String"
        // Method check
        val has = hasLowerCase(string)
        // Manual check
        val manualCheck = string.contains("[a-z]".toRegex())
        // Must be both true
        assertThat(manualCheck && has).isTrue()
    }

    @Test
    fun hasDigit_isTrue() {
        val string = "some 3String"
        // Method check
        val has = hasDigit(string)
        // Manual check
        val manualCheck = string.contains("[0-9]".toRegex())
        // Must be both true
        assertThat(manualCheck && has).isTrue()
    }

    @Test
    fun hasSpecialCharacter_isTrue() {
        val string = "some> String"
        // Method check
        val has = hasSpecialCharacter(string)
        // Manual check
        val manualCheck = string.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())
        // Must be both true
        assertThat(manualCheck && has).isTrue()
    }


}