package dev.saketanand.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.saketanand.auth.domain.PasswordValidationState
import dev.saketanand.auth.domain.UserDataValidator
import dev.saketanand.auth.presentation.R
import dev.saketanand.core.presentation.designsystem.CheckIcon
import dev.saketanand.core.presentation.designsystem.CrossIcon
import dev.saketanand.core.presentation.designsystem.EmailIcon
import dev.saketanand.core.presentation.designsystem.Poppins
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.RuniqueDarkRed
import dev.saketanand.core.presentation.designsystem.RuniqueGray
import dev.saketanand.core.presentation.designsystem.RuniqueGreen
import dev.saketanand.core.presentation.designsystem.components.ActionButton
import dev.saketanand.core.presentation.designsystem.components.GradientBackground
import dev.saketanand.core.presentation.designsystem.components.HorizontalGap
import dev.saketanand.core.presentation.designsystem.components.RunTrackerPasswordTextField
import dev.saketanand.core.presentation.designsystem.components.RunTrackerTextField
import dev.saketanand.core.presentation.designsystem.components.VerticalGap
import dev.saketanand.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterVieModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.event) {event->
        when(event) {
            is RegisterEvent.Error ->{
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_SHORT).show()
                onSuccessfulRegistration()
            }
        }
    }
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    val scrollState = rememberScrollState()
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RuniqueGray
                    )
                ) {
                    append(stringResource(R.string.already_have_an_account) + " ")
                    withLink(
                        link = LinkAnnotation.Clickable(
                            tag = "login_tag",
                            // Handle the action directly inside the link definition
                            linkInteractionListener = {
                                onAction(RegisterAction.OnLoginClick)
                            }
                        )
                    ) {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = Poppins
                            )
                        ) {
                            append(stringResource(R.string.login))
                        }
                    }
                }
            }
            Text(text = annotatedString)
            VerticalGap(height = 48.dp)
            RunTrackerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email
            )
            VerticalGap(height = 16.dp)

            RunTrackerPasswordTextField(
                state = state.password,
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                }
            )

            VerticalGap(height = 16.dp)

            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ), isValid = state.passwordValidationState.hasMinLength
            )
            VerticalGap(height = 4.dp)

            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_one_number,
                ), isValid = state.passwordValidationState.hasNumber
            )

            VerticalGap(height = 4.dp)
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_lowercase_char,
                ), isValid = state.passwordValidationState.hasLowercaseCharacter
            )

            VerticalGap(height = 4.dp)
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_uppercase_char,
                ), isValid = state.passwordValidationState.hasUppercaseCharacter
            )

            VerticalGap(height = 32.dp)
            ActionButton(
                text = stringResource(R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )

        }
    }
}

@Composable
fun PasswordRequirement(modifier: Modifier = Modifier, text: String, isValid: Boolean) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) RuniqueGreen else RuniqueDarkRed
        )
        HorizontalGap(width = 16.dp)
        Text(text, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
    }
}

@Preview
@Composable
private fun RegisterScreenPrev() {
    RunTrackerTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true,
                    hasMinLength = true,
                    hasLowercaseCharacter = true,
                    hasUppercaseCharacter = true
                ),
            ),
            onAction = {}
        )

    }

}