package dev.saketanand.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.StartIcon
import dev.saketanand.core.presentation.designsystem.StopIcon
import dev.saketanand.core.presentation.designsystem.components.ActionButton
import dev.saketanand.core.presentation.designsystem.components.AppFloatingActioButton
import dev.saketanand.core.presentation.designsystem.components.AppToolbar
import dev.saketanand.core.presentation.designsystem.components.OutlineActionButton
import dev.saketanand.core.presentation.designsystem.components.RunTrackerDialog
import dev.saketanand.core.presentation.designsystem.components.RunTrackerScaffold
import dev.saketanand.run.presentation.R
import dev.saketanand.run.presentation.active_run.component.RunDataCard
import dev.saketanand.run.presentation.active_run.maps.TrackerMap
import dev.saketanand.run.presentation.util.hasLocationPermission
import dev.saketanand.run.presentation.util.hasNotificationPermission
import dev.saketanand.run.presentation.util.shouldShowLocationPermissionRationale
import dev.saketanand.run.presentation.util.shouldShowNotificationPermissionRationale
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

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCoarseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission =
            if (Build.VERSION.SDK_INT > 33) perms[Manifest.permission.POST_NOTIFICATIONS] == true else true


        val activity = context as ComponentActivity

        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermission(
                acceptedLocationPermission = hasCoarseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermission(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRationale = showNotificationRationale
            )
        )


    }
    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()



        onAction(
            ActiveRunAction.SubmitLocationPermission(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermission(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRationale = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestPermissions(context)
        }
    }
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
                contentDescription = if (state.shouldTrack) stringResource(R.string.pause) else stringResource(
                    R.string.start
                )
            )
        },
        content = { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TrackerMap(
                    isRunFinished = state.isRunFinished,
                    currentLocation = state.currentLocation,
                    locations = state.runData.locations,
                    modifier = Modifier.fillMaxSize()
                ) { }
                RunDataCard(
                    runData = state.runData,
                    elapsedTime = state.elapsedTime,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(padding)
                        .fillMaxWidth()
                )
            }
        }

    )
    if (!state.shouldTrack && state.hasStartedRunning) {
        RunTrackerDialog(
            title = stringResource(R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(R.string.resume_or_finish_run),
            primaryButton = {
                ActionButton(
                    text = stringResource(R.string.resume),
                    isLoading = false,
                    modifier = Modifier.weight(1f)

                ) {
                    onAction(ActiveRunAction.OnResumeRunClick)
                }
            },
            secondaryButton = {
                OutlineActionButton(
                    text = stringResource(R.string.finish),
                    isLoading = state.isSavingRun,
                    modifier = Modifier.weight(1f)
                ) {
                    onAction(ActiveRunAction.OnFinishedRunClick)
                }
            }
        )
    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RunTrackerDialog(
            title = stringResource(R.string.permission_required),
            onDismiss = {},
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> stringResource(R.string.location_notification_rationale)
                state.showNotificationRationale -> stringResource(R.string.notification_rationale)
                else -> stringResource(R.string.location_rationale)
            },
            primaryButton = {
                OutlineActionButton(
                    text = stringResource(R.string.okay),
                    isLoading = false
                ) {
                    onAction(ActiveRunAction.DismissRationaleDialog)
                    permissionLauncher.requestPermissions(context)
                }
            },
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val notificationPermission =
        if (Build.VERSION.SDK_INT >= 33) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()

    when {
        !hasLocationPermission && !hasNotificationPermission -> launch(locationPermission + notificationPermission)
        !hasLocationPermission -> launch(locationPermission)
        !hasNotificationPermission -> launch(notificationPermission)
    }
}

@Preview
@Composable
private fun ActionRunScreenPreview() {
    RunTrackerTheme {
        ActiveRunScreen(state = ActiveRunState(), onAction = {})
    }

}