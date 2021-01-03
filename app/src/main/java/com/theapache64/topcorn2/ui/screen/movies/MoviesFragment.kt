package com.theapache64.topcorn2.ui.screen.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.theapache64.topcorn2.ui.theme.TopCornTheme

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 22:47
 */
class MoviesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TopCornTheme {
                    Text(text = "Movies")
                }
            }
        }
    }
}