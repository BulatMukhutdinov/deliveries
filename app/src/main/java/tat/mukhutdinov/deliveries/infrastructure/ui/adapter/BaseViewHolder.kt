package tat.mukhutdinov.deliveries.infrastructure.ui.adapter

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import tat.mukhutdinov.deliveries.BR
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery

abstract class BaseViewHolder<B : BaseViewHolderBindings, V : ViewDataBinding>(
    protected val viewDataBinding: V,
    protected val bindings: B
) : RecyclerView.ViewHolder(viewDataBinding.root), LifecycleOwner {

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    @CallSuper
    open fun bindTo(delivery: Delivery? = null) {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        viewDataBinding.setVariable(BR.bindings, bindings)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    @CallSuper
    open fun onViewRecycled() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        viewDataBinding.unbind()
    }
}