package dev.saketanand.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RunTrackerScaffold(
    modifier: Modifier = Modifier,
    withGradient: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) { innerPadding ->
        if (withGradient) {
            GradientBackground {
                content(innerPadding)
            }
        } else content(innerPadding)
    }

}