package dev.saketanand.run.presentation.active_run

sealed interface ActiveRunAction {
    data object OnToggleRunClick : ActiveRunAction
    data object OnFinishedRunClick : ActiveRunAction
    data object OnResumeRunClick : ActiveRunAction
    data object OnBackClick : ActiveRunAction

    data class SubmitLocationPermission(
        val acceptedLocationPermission : Boolean,
        val showLocationRationale : Boolean
    ): ActiveRunAction

    data class SubmitNotificationPermission(
        val acceptedNotificationPermission : Boolean,
        val showNotificationRationale : Boolean
    ): ActiveRunAction

    data object DismissRationaleDialog: ActiveRunAction

}