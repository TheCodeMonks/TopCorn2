package com.theapache64.topcorn2.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.theapache64.topcorn2.R

/**
 * Thanks for the name @funky_muse
 * https://twitter.com/funky_muse/status/1346504497082871812
 */
@Composable
fun RetryMessage(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .requiredSize(48.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_warning),
            contentDescription = null
        )

        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                top = 15.dp,
                bottom = 20.dp
            )
        )

        Button(onClick = { onRetryClicked() }) {
            Text(text = stringResource(id = R.string.any_retry))
        }
    }
}