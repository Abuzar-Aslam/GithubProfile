package home.assignemnt.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import home.assignemnt.presentation.R
import home.assignemnt.presentation.model.UserProfileViewData
import home.assignemnt.design.util.Dimens
import home.assignemnt.design.util.Spacing

/**
 * Composable function to display the profile header.
 *
 * @param profile The user profile data to display.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileHeader(profile: UserProfileViewData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(profile.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.profileImageSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(Spacing.x2))
            Column {
                Text(
                    text = profile.name ?: "",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.company ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.x4))

        Text(
            text = profile.email,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(Spacing.x4))

        Row {
            Text(
                text = stringResource(R.string.profile_screen_followers, profile.followers),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(Spacing.x4))
            Text(
                text = stringResource(R.string.profile_screen_following, profile.following),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}