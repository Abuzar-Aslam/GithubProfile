package home.assignemnt.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import home.assignemnt.design.util.Dimens
import home.assignemnt.design.util.Spacing
import home.assignemnt.design.R
import home.assignemnt.presentation.model.UserRepositoryViewData

/**
 * Composable function to display a vertical list of repositories.
 *
 * @param avatarUrl The URL of the user's avatar image (nullable).
 * @param company The name of the company the user is associated with (nullable).
 * @param repositories A list of repository view data to display.
 */
@Composable
fun VerticalRepositoryList(
    avatarUrl: String?,
    company: String?,
    repositories: List<UserRepositoryViewData>
) {
    Column {
        repositories.forEach { repo ->
            RepositoryItem(avatarUrl, company, repo)
            Spacer(modifier = Modifier.height(Spacing.x2))
        }
    }
}

/**
 * Composable function to display a repository item.
 *
 * @param avatarUrl The URL of the user's avatar image (nullable).
 * @param company The name of the company the user is associated with (nullable).
 * @param repo The repository view data.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun RepositoryItem(avatarUrl: String?, company: String?, repo: UserRepositoryViewData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(Spacing.x0_25, Color.LightGray, RoundedCornerShape(Spacing.x2)),
        shape = RoundedCornerShape(Spacing.x2),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.x0_25)
    ) {
        Column(modifier = Modifier.padding(Spacing.x4)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(avatarUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimens.repositoryCardImageSize)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(Spacing.x2))
                Text(
                    text = company ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(Spacing.x1))
            Text(
                text = repo.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Spacing.x2))
            Text(
                text = repo.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(Spacing.x2))

            Row {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = null,
                        modifier = Modifier.size(Spacing.x3)
                    )
                    Spacer(modifier = Modifier.width(Spacing.x1))
                    Text(
                        text = "${repo.stargazerCount}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.width(Spacing.x4))
                repo.language?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_circle),
                            contentDescription = null,
                            modifier = Modifier.size(Spacing.x3)
                        )
                        Spacer(modifier = Modifier.width(Spacing.x1))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
