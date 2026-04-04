package dev.saketanand.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {
    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hsaMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasUppercaseCharacter = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasLowercaseCharacter = password.any { it.isLowerCase() }
        return PasswordValidationState(
            hasNumber = hasDigit,
            hasMinLength = hsaMinLength,
            hasLowercaseCharacter = hasLowercaseCharacter,
            hasUppercaseCharacter = hasUppercaseCharacter
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}