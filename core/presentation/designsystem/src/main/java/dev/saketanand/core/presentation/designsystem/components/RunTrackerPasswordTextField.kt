package dev.saketanand.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.saketanand.core.presentation.designsystem.EyeClosedIcon
import dev.saketanand.core.presentation.designsystem.EyeOpenedIcon
import dev.saketanand.core.presentation.designsystem.LockIcon
import dev.saketanand.core.presentation.designsystem.RunTrackerTheme

@Composable
fun RunTrackerPasswordTextField(
    state: TextFieldState,
    hint: String,
    title: String?,
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        VerticalGap(height = 4.dp)
        BasicSecureTextField(
            state = state,
            textObfuscationMode = if (isPasswordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = LockIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalGap(width = 16.dp)

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (!isPasswordVisible) EyeClosedIcon else EyeOpenedIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }

            }
        )
    }
}

@Preview
@Composable
private fun RunTrackerPasswordTextFieldPrev() {
    RunTrackerTheme {
        RunTrackerPasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            title = "Email",
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }

}