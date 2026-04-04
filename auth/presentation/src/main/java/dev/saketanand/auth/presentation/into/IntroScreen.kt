package dev.saketanand.auth.presentation.into

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.saketanand.auth.presentation.R
import dev.saketanand.core.presentation.designsystem.LogoIcon
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.components.ActionButton
import dev.saketanand.core.presentation.designsystem.components.GradientBackground
import dev.saketanand.core.presentation.designsystem.components.OutlineActionButton
import dev.saketanand.core.presentation.designsystem.components.VerticalGap

@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignUpClick -> onSignUpClick()
                IntroAction.OnSingInClick -> onSignInClick()
            }

        }
    )
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {
    GradientBackground(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_to_runtracker),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
            VerticalGap(height = 8.dp)
            Text(
                text = stringResource(R.string.run_tracker_description),
                style = MaterialTheme.typography.bodySmall
            )
            VerticalGap(height = 32.dp)
            OutlineActionButton(
                text = stringResource(R.string.sign_in),
                isLoading = false
            ) { onAction(IntroAction.OnSingInClick) }

            VerticalGap(height = 16.dp)
            ActionButton(
                text = stringResource(R.string.sign_up),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(IntroAction.OnSignUpClick) }
            )
        }
    }
}

@Composable
fun LogoVertical(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.runtracker),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Preview
@Composable
private fun IntroScreenPreview() {
    RunTrackerTheme {
        IntroScreen {}
    }
}