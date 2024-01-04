package itis.lisa.semestrovka.basicfeature.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import itis.lisa.semestrovka.basicfeature.R
import itis.lisa.semestrovka.basicfeature.presentation.model.NewsDisplayable

const val NEWS_DIVIDER_TEST_TAG = "newsDividerTestTag"

@Composable
fun NewsListContent(
    newsList: List<NewsDisplayable>,
    modifier: Modifier = Modifier,
    onNewsClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimen_medium),
            ),
    ) {
        itemsIndexed(
            items = newsList,
            key = { _, news -> news.id },
        ) { index, item ->
            NewsItem(
                news = item,
                onNewsClick = { onNewsClick(item.url) },
            )

            if (index < newsList.lastIndex) {
                Divider(
                    modifier = Modifier.testTag(NEWS_DIVIDER_TEST_TAG),
                )
            }
        }
    }
}
