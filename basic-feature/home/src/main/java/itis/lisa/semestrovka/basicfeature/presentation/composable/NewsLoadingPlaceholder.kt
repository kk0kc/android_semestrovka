package itis.lisa.semestrovka.basicfeature.presentation.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsLoadingPlaceholder(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .fillMaxSize(),
    )
}
