package com.theapache64.topcorn2.utils.flow

import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by theapache64 : Jan 08 Fri,2021 @ 01:40
 */
fun <T> mutableEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1
    )
}