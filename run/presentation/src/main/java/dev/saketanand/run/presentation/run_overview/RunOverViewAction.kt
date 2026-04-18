package dev.saketanand.run.presentation.run_overview

sealed interface RunOverViewAction {
    data object OnStartClick: RunOverViewAction
    data object OnLogOutClick: RunOverViewAction
    data object OnAnalyticsClick: RunOverViewAction
}