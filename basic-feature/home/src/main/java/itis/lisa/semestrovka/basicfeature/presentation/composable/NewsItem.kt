package itis.lisa.semestrovka.basicfeature.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import itis.lisa.semestrovka.basicfeature.R
import itis.lisa.semestrovka.basicfeature.presentation.model.NewsDisplayable
import itis.lisa.semestrovka.core.design.Typography

@Composable
fun NewsItem(
    news: NewsDisplayable,
    modifier: Modifier = Modifier,
    onNewsClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.dimen_medium),
            )
            .clickable { onNewsClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.dimen_small),
            ),
        ) {
            Text(
                text = news.title,
                style = Typography.titleMedium,
            )

            Text(
                text = news.author,
                style = Typography.bodyMedium,
            )

            Text(
                text = news.description,
                style = Typography.bodyMedium,
            )

            Text(
                text = news.category,
                style = Typography.bodyMedium,
            )

            Text(
                text = news.published,
                style = Typography.bodyMedium,
            )

        }

        AsyncImage(
            model = news.image,
            contentDescription = stringResource(id = R.string.news_image_content_description),
            modifier = Modifier
                .weight(1f),
        )
    }
}
