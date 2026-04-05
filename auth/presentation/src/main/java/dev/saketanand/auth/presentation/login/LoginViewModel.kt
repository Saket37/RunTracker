package dev.saketanand.auth.presentation.login

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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository, private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventsChannel = Channel<LoginEvent>()
    val events = eventsChannel.receiveAsFlow()

    init {
        observeStateChange()
    }

    private fun observeStateChange() {
        snapshotFlow {
            val email = state.email.text.toString().trim()
            val password = state.password.text.toString()
            userDataValidator.isValidEmail(email) && password.isNotEmpty()
        }.distinctUntilChanged().onEach { isValid ->
                state = state.copy(canLogin = isValid)
            }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClicked -> login()
            LoginAction.OnTogglePasswordVisibility -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)

            else -> Unit
        }

    }

    private fun login() = viewModelScope.launch {
        state = state.copy(
            isLoggingIn = true
        )
        val result = authRepository.login(
            email = state.email.text.toString().trim(), password = state.password.text.toString()
        )
        state = state.copy(isLoggingIn = false)
        when (result) {
            is Result.Error -> {
                if (result.error == DataError.Network.UNAUTHORIZED) {
                    eventsChannel.trySend(LoginEvent.Error(UiText.StringResource(R.string.error_email_password_incorrect)))
                } else eventsChannel.trySend(LoginEvent.Error(result.error.asUiText()))
            }

            is Result.Success -> {
                eventsChannel.trySend(LoginEvent.LoginSuccess)
            }
        }
    }
}