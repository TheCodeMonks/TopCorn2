package com.theapache64.topcorn2.ui.screen.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.theapache64.topcorn2.R
import com.theapache64.topcorn2.ui.main.MainViewModel
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 22:47
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        Timber.d("onCreateView: Again called : $viewModel")

        viewModel.goToMovieDetail.observe(viewLifecycleOwner) { movieId ->
            Timber.d("onCreateView: MovieId is $movieId")
            if (movieId != -1) {
                val navController = findNavController()

                navController.navigate(
                    MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movieId)
                )
            }
        }

        viewModel.toggleDarkMode.asLiveData().observe(viewLifecycleOwner) {
            it?.let { isDark ->
                Timber.d("onCreateView: Fired!!!")
                val flag = if (isDark) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(flag)
            }
        }


        viewModel.sortOrderToast.asLiveData().observe(viewLifecycleOwner) { sortOrder ->
            val msg = when (sortOrder) {
                MoviesViewModel.SORT_ORDER_YEAR -> R.string.movies_sort_order_changed_year
                MoviesViewModel.SORT_ORDER_RATING -> R.string.movies_sort_order_changed_rating
                else -> -1
            }

            if (msg != -1) {
                Toast.makeText(
                    requireContext(),
                    msg,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TopCornTheme {
                    MoviesScreen(viewModel)
                }
            }
        }
    }
}