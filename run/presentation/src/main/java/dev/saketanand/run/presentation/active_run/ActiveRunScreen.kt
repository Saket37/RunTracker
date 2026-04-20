package dev.saketanand.run.presentation.active_run

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.StartIcon
import dev.saketanand.core.presentation.designsystem.StopIcon
import dev.saketanand.core.presentation.designsystem.components.AppFloatingActioButton
import dev.saketanand.core.presentation.designsystem.components.AppToolbar
import dev.saketanand.core.presentation.designsystem.components.RunTrackerScaffold
import dev.saketanand.run.presentation.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActiveRunScreen(state: ActiveRunState, onAction: (ActiveRunAction) -> Unit) {
    RunTrackerScaffold(
        withGradient = false,
        topAppBar = {
            AppToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackClick = { onAction(ActiveRunAction.OnBackClick) },
            )
        },
        floatingActionButton = {
            AppFloatingActioButton(
                icon = if (state.shouldTrack) StopIcon else StartIcon,
                onClick = { onAction(ActiveRunAction.OnToggleRunClick) },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) stringResource(R.string.pause) else stringResource(R.string.start)
            )
        },
        content = {}
    )
}

@Preview
@Composable
private fun ActionRunScreenPreview() {
    RunTrackerTheme {
        ActiveRunScreen(state = ActiveRunState(), onAction = {})
    }

}