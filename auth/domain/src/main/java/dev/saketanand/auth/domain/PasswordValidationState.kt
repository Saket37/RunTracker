package dev.saketanand.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasUppercaseCharacter: Boolean = false,
    val hasLowercaseCharacter: Boolean = false,
    val hasNumber: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasUppercaseCharacter && hasLowercaseCharacter && hasNumber
}

