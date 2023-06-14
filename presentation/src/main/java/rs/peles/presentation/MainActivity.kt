package rs.peles.presentation

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import rs.peles.presentation.base.BaseActivity
import rs.peles.presentation.databinding.ActivityMainBinding
import rs.peles.presentation.util.hide
import rs.peles.presentation.util.show

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindLayout: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {

    }

    fun showProgressBar(show: Boolean) {
        if(show) binding.progressBar.show()
        else binding.progressBar.hide()
    }

}