package com.theapache64.topcorn2.ui.screen.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.theapache64.topcorn2.ui.main.MainViewModel
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

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
            moviesViewModel.movies.collect {
                Timber.d("onCreateView: $it")
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