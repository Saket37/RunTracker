package dev.saketanand.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.saketanand.auth.presentation.R
import dev.saketanand.core.presentation.designsystem.EmailIcon
import dev.saketanand.core.presentation.designsystem.Poppins
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.RuniqueGray
import dev.saketanand.core.presentation.designsystem.components.ActionButton
import dev.saketanand.core.presentation.designsystem.components.GradientBackground
import dev.saketanand.core.presentation.designsystem.components.RunTrackerPasswordTextField
import dev.saketanand.core.presentation.designsystem.components.RunTrackerTextField
import dev.saketanand.core.presentation.designsystem.components.VerticalGap
import dev.saketanand.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onSignUpClick:() -> Unit,
    onLoginSuccess :() -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) {event ->
        when(event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }
            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, R.string.youre_logged_in, Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
        }
    }
    LoginScreen(
        state = viewModel.state,
        onAction = {action ->
            when(action) {
                LoginAction.OnRegisterClicked -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val scrollState = rememberScrollState()
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                stringResource(R.string.hi_there),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                stringResource(R.string.welcome_text),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            VerticalGap(height = 48.dp)
            RunTrackerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth()
            )
            VerticalGap(16.dp)
            RunTrackerPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(),
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibility)
                }
            )

            VerticalGap(32.dp)
            ActionButton(
                text = stringResource(R.string.login),
                isLoading = state.isLoggingIn,
                enabled = state.canLogin,
                onClick = {
                    onAction(LoginAction.OnLoginClicked)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = RuniqueGray
                        )
                    ) {
                        append(stringResource(R.string.dont_have_an_account) + " ")
                        withLink(
                            link = LinkAnnotation.Clickable(
                                tag = "signup_tag",
                                // Handle the action directly inside the link definition
                                linkInteractionListener = {
                                    onAction(LoginAction.OnRegisterClicked)
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
                                append(stringResource(R.string.register))
                            }
                        }
                    }
                }
                Text(text = annotatedString)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    RunTrackerTheme {
        LoginScreen(
            LoginState()
        ) { }
    }

}