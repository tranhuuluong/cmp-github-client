package io.github.tranhuuluong.kmpgithubclient.design_system.utils

import androidx.compose.foundation.lazy.LazyListState

/**
 * Checks whether the scroll position has reached (or is near) the bottom of the list.
 *
 * @param buffer The number of items from the bottom to consider as "close enough".
 * For example, a buffer of 2 will return true when the user scrolls to within
 * 2 items of the end.
 * @return true if the last visible item is within [buffer] items from the end of the list.
 */
inline fun LazyListState.reachedBottom(buffer: Int = 0): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem != null && lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - buffer
}