package dev.saketanand.auth.presentation.login

import dev.saketanand.core.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText) : LoginEvent
    data object LoginSuccess: LoginEvent
}