package com.theapache64.topcorn2.ui.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.theapache64.topcorn2.ui.main.MainViewModel
import com.theapache64.topcorn2.ui.theme.TopCornTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:20
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        Timber.d("onCreateView: MainVm is $mainViewModel")
        lifecycleScope.launchWhenStarted {
            splashViewModel.isSplashTimeFinished.collect { isSplashTimeFinished ->
                Timber.d("onCreateView: Should launch is $isSplashTimeFinished")

                if (isSplashTimeFinished) {
                    val navController = findNavController()

                    navController.navigate(
                        SplashFragmentDirections.actionSplashFragmentToMoviesFragment()
                    )
                }
            }
        }

        setContent {
            TopCornTheme {
                SplashScreen(splashViewModel)
            }
        }
    }
}