package demo.timer.app.timer

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import demo.timer.app.R.layout.fragment_timer
import demo.timer.app.databinding.FragmentTimerBinding.bind
import kotlinx.coroutines.launch
import xyz.tynn.astring.core.setText

@AndroidEntryPoint
class TimerFragment : Fragment(fragment_timer) {

    private val viewModel by viewModels<TimerViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) = with(bind(view)) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timer.collect {
                timer.setText(it)
                start.isGone = it.isStarted
                pause.isVisible = it.isStarted
            }
        }

        pause.setOnClickListener { viewModel.pause() }
        start.setOnClickListener { viewModel.start() }
        stop.setOnClickListener { viewModel.stop() }
    }
}
