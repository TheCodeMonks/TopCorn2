package com.theapache64.topcorn2.ui.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:20
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {


        // Invoked when splash timer finished
        viewModel.isSplashTimeFinished.asLiveData()
            .observe(viewLifecycleOwner) { isSplashTimeFinished ->

                if (isSplashTimeFinished) {
                    val navController = findNavController()

                    // Showing movies (listing) fragment
                    navController.navigate(
                        SplashFragmentDirections.actionSplashFragmentToMoviesFragment()
                    )
                }
            }

        setContent {
            TopCornTheme {
                SplashScreen(viewModel)
            }
        }
    }
}