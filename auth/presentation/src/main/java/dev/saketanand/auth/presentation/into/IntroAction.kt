package dev.saketanand.auth.presentation.into

sealed interface IntroAction {
    data object OnSingInClick : IntroAction
    data object OnSignUpClick : IntroAction
}