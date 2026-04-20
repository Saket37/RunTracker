package dev.saketanand.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.saketanand.core.presentation.designsystem.AnalyticsIcon
import dev.saketanand.core.presentation.designsystem.LogoIcon
import dev.saketanand.core.presentation.designsystem.LogoutIcon
import dev.saketanand.core.presentation.designsystem.RunIcon
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.components.AppFloatingActioButton
import dev.saketanand.core.presentation.designsystem.components.AppToolbar
import dev.saketanand.core.presentation.designsystem.components.RunTrackerScaffold
import dev.saketanand.core.presentation.designsystem.components.util.DropDownItem
import dev.saketanand.run.presentation.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onStartRunClick:()-> Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = {action->
            when(action) {
                RunOverViewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunOverviewScreen(
    onAction: (RunOverViewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)

    RunTrackerScaffold(
        topAppBar = {
            AppToolbar(
                title = stringResource(R.string.runtracker),
                showBackButton = false,
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(30.dp)
                    )
                },
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(R.string.log_out)
                    )
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverViewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverViewAction.OnLogOutClick)
                    }

                }
            )
        },
        floatingActionButton = {
            AppFloatingActioButton(
                icon = RunIcon,
                onClick = { onAction(RunOverViewAction.OnStartClick) }
            )
        },
        content = { padding ->


        }
    )
}


@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RunTrackerTheme {
        RunOverviewScreen() {}
    }
}