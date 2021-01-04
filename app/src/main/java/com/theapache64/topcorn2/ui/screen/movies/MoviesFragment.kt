package com.theapache64.topcorn2.ui.screen.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.theapache64.topcorn2.ui.main.MainViewModel
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 22:47
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        lifecycleScope.launchWhenStarted {
            moviesViewModel.toggleDarkMode.collect {
                it?.let { isDark ->
                    val flag = if (isDark) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_NO
                    }
                    AppCompatDelegate.setDefaultNightMode(flag)
                }
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TopCornTheme {
                    MoviesScreen(moviesViewModel)
                }
            }
        }
    }
}