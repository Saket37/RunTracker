@file:OptIn(FlowPreview::class)

package dev.saketanand.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.saketanand.auth.domain.AuthRepository
import dev.saketanand.auth.domain.UserDataValidator
import dev.saketanand.auth.presentation.R
import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.Result
import dev.saketanand.core.presentation.ui.UiText
import dev.saketanand.core.presentation.ui.asUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterVieModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val event = eventChannel.receiveAsFlow()


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
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick ->state =  state.copy(isPasswordVisible = !state.isPasswordVisible)
            else -> Unit
        }

    }
  //  {"email":"test@gmail.com","password":"Test123456"}
    private fun register() = viewModelScope.launch {
        state = state.copy(isRegistering = true)
        val result = authRepository.register(
            email = state.email.text.toString().trim(),
            password = state.password.text.toString()
        )

        state = state.copy(isRegistering = false)

        when (result) {
            is Result.Error -> {
                if (result.error == DataError.Network.CONFLICT) {
                    eventChannel.trySend(RegisterEvent.Error(UiText.StringResource(R.string.error_email_exists)))
                } else {
                    eventChannel.trySend(RegisterEvent.Error(result.error.asUiText()))
                }

            }

            is Result.Success -> {
                eventChannel.trySend(RegisterEvent.RegistrationSuccess)
            }
        }

    }
}