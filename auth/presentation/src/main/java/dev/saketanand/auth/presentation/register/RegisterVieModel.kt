@file:OptIn(FlowPreview::class)

package dev.saketanand.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.saketanand.auth.domain.UserDataValidator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class RegisterVieModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        observeEmailChange()
        observePasswordChange()
    }

    @OptIn(FlowPreview::class)
    private fun observeEmailChange() {
        snapshotFlow { state.email.text }
            .map { it.toString() }.distinctUntilChanged().debounce(500L).onEach {
                val isValid = userDataValidator.isValidEmail(it)
                state = state.copy(
                    isEmailValid = isValid,
                    canRegister = isValid && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    private fun observePasswordChange() {
        snapshotFlow { state.password.text }
            .map { it.toString() }.distinctUntilChanged().debounce(500L).onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password)
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword && !state.isRegistering

                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {

    }

}