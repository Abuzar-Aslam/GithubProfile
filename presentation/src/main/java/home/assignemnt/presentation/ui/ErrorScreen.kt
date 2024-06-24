package home.assignemnt.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import home.assignemnt.design.util.Spacing
import home.assignemnt.presentation.R
import home.assignemnt.design.R as designR

/**
 * Composable function to display an error state with an image, title, message, and a retry button.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param onRetry Lambda function to be called when the retry action is triggered.
 */
@Composable
fun ErrorState(modifier: Modifier = Modifier, onRetry: () -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onRetry
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = designR.drawable.icon_github),
            contentDescription = "errorStatePlaceHolderImage",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
        )
        Text(
            modifier = Modifier.padding(top = Spacing.x4),
            text = stringResource(R.string.error_screen_title),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier.padding(top = Spacing.x2),
            text = stringResource(R.string.error_screen_message),
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier.padding(top = Spacing.x2),
            text = stringResource(R.string.error_screen_button_text),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
