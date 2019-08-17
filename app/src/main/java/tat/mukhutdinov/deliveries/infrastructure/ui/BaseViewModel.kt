package tat.mukhutdinov.deliveries.infrastructure.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import tat.mukhutdinov.deliveries.BR

abstract class BaseViewModel<B : BaseBindings, V : ViewDataBinding> : Fragment(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    protected abstract val layoutId: Int

    protected abstract val bindings: B

    protected lateinit var viewBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.lifecycleOwner = viewLifecycleOwner

        viewBinding.setVariable(BR.bindings, bindings)
    }

    override fun onDestroy() {
        cancel()

        super.onDestroy()
    }

    fun navigateTo(action: NavDirections) {
        view?.findNavController()?.navigate(action)
    }

    fun navigateUp() {
        view?.findNavController()?.navigateUp()
    }
}