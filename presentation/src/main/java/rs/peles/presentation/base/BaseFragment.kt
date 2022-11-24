package rs.peles.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Base class for all [Fragment] instances
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding : VB
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindLayout.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    /**
     * Do an action on UI thread.
     */
    fun doOnUI(func: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch { func() }
    }

    /**
     * Do an action on IO thread.
     */
    fun doOnIO(func: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch { func() }
    }

    /**
     * Show snack bar.
     */
    fun makeText(view: View, text: String, listener: View.OnClickListener? = null) {
        Snackbar
            .make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", listener)
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}