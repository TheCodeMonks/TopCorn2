package com.theapache64.topcorn2.ui.screen.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by theapache64 : Jan 05 Tue,2021 @ 01:08
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.init(args.movieId)

        lifecycleScope.launchWhenStarted {
            viewModel.openImdbUrl.collect { imdbUrl ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(imdbUrl)
                    )
                )
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TopCornTheme {
                    MovieDetailScreen(
                        viewModel
                    )
                }
            }
        }
    }
}