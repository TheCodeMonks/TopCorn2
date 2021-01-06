package com.theapache64.topcorn2.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
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
                .preferredSize(48.dp),
            imageVector = vectorResource(id = R.drawable.ic_warning)
        )

        Text(
            text = message,
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 10.dp
            )
        )

        Button(onClick = { onRetryClicked() }) {
            Text(text = "RETRY")
        }
    }
}