package dev.saketanand.run.presentation.active_run.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme
import dev.saketanand.core.presentation.designsystem.components.VerticalGap
import dev.saketanand.core.presentation.ui.R
import dev.saketanand.core.presentation.ui.formatted
import dev.saketanand.core.presentation.ui.toFormattedKms
import dev.saketanand.core.presentation.ui.toFormattedPace
import dev.saketanand.run.domian.RunData
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun RunDataCard(modifier: Modifier = Modifier, runData: RunData, elapsedTime: Duration) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunDataItem(
            title = stringResource(R.string.duration),
            value = elapsedTime.formatted(),
            valueFontSize = 32.sp
        )
        VerticalGap(24.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RunDataItem(
                title = stringResource(R.string.distance),
                value = (runData.distanceInMeters / 1000.0).toFormattedKms(),
                modifier = Modifier.defaultMinSize(minWidth = 75.dp)
            )
            RunDataItem(
                title = stringResource(R.string.pace),
                value = elapsedTime.toFormattedPace(
                    distanceKms = runData.distanceInMeters / 1000.0
                ),
                modifier = Modifier.defaultMinSize(minWidth = 75.dp)
            )

        }
    }
}

@Composable
private fun RunDataItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueFontSize: TextUnit = 16.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
        Text(value, color = MaterialTheme.colorScheme.onSurface, fontSize = valueFontSize)
    }
}

@Preview
@Composable
private fun RunDataCardPreview() {

    RunTrackerTheme {
        RunDataCard(
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceInMeters = 3425,
                pace = 5.minutes
            )
        )

    }

}